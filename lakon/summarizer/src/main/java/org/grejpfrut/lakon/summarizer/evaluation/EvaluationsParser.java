package org.grejpfrut.lakon.summarizer.evaluation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Parses file with evaluations allows to get various information from this
 * file, and file with texts used in evaluation. This class also offers a method
 * which does the simple validation of given summaries (see 
 * {@link EvaluationsParser#validateEvaluation(Evaluation, Article)}).
 * 
 * @author Adam Dudczak
 */
public class EvaluationsParser {

	private final static Logger logger = Logger
			.getLogger(EvaluationsParser.class);

	private List<Evaluation> evaluations = new ArrayList<Evaluation>();

	private final Map<Integer, Article> articles;

	Map<Integer, Integer> summariesLength;

	/**
	 * @param articles - map of article ids to text of those articles.
	 * @param summariesLength - holds article ids mapped to summaries lenghts.
	 */
	public EvaluationsParser(Map<Integer, Article> articles,
			Map<Integer, Integer> summariesLength) {

		this.articles = articles;
		this.summariesLength = summariesLength;

		try {
			parse();
		} catch (Exception e) {
			throw new RuntimeException("Exception while parsing evaluations", e);
		}

		List<Evaluation> validated = new ArrayList<Evaluation>();
		int wrongEvals = 0;
		for (Evaluation eval : this.evaluations) {
			Article art = this.articles.get(eval.getTextId());
			if (art == null)
				continue;
			if (this.validateEvaluation(eval, art))
				validated.add(eval);
			else
				wrongEvals++;
		}
		this.evaluations = validated;
	}

	/**
	 * Parses xml file with data from experiment.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private void parse() throws ParserConfigurationException, SAXException,
			IOException {

		InputStream is = this.getClass()
				.getResourceAsStream("/evaluations.xml");

		if (is != null) {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(is));

			NodeList nodes = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node eNode = nodes.item(i);
				if ("evaluation".equals(eNode.getNodeName())) {
					NodeList childs = eNode.getChildNodes();
					Evaluation newEvaluation = new Evaluation();
					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);
						if (node.getNodeType() != Node.ELEMENT_NODE)
							continue;
						String value = node.getFirstChild().getTextContent();
						if ("user".equals(node.getNodeName())) {
							newEvaluation.setUser(value);
						} else if ("artId".equals(node.getNodeName())) {
							newEvaluation.setTextId(Integer.parseInt(value));
						} else if ("sentences".equals(node.getNodeName())) {
							String[] chosen = value.split(",");
							newEvaluation.setChosen(chosen);
						}
					}
					this.evaluations.add(newEvaluation);
				}
			}
		}

	}

	/**
	 * @return Gets list of all users who participated in experiment.
	 */
	public List<String> getUsers() {
		Set<String> users = new HashSet<String>();
		for (Evaluation eval : this.evaluations) {
			users.add(eval.getUser());
		}
		return new ArrayList<String>(users);
	}
	
	/**
	 * @return Gets users ranking.
	 */
	public Map<String, Integer> getUsersRanking(){
		Map<String, Integer> ranking = new HashMap<String,Integer>();
		for (Evaluation eval : this.evaluations) {
			Integer counter = ranking.get(eval.getUser());
			if (counter != null )
				 ranking.put(eval.getUser(), new Integer(counter+1));
			else
				 ranking.put(eval.getUser(), new Integer(1));
		}
		return ranking;
	}

	public List<String> getUsers(int artId) {
		Set<String> users = new HashSet<String>();
		for (Evaluation eval : this.evaluations) {
			if (eval.getTextId() == artId)
				users.add(eval.getUser());
		}
		return new ArrayList<String>(users);
	}

	/**
	 * @return Gets map with number of evaluations for given text.
	 */
	public Map<Integer, Integer> getEvaluationsSummary() {
		Map<Integer, Integer> evalSumm = new HashMap<Integer, Integer>();
		for (Evaluation eval : this.evaluations) {
			int artId = eval.getTextId();
			Integer counter = evalSumm.get(artId);
			if (counter == null)
				evalSumm.put(artId, new Integer(1));
			else
				evalSumm.put(artId, new Integer(counter.intValue() + 1));
		}
		return evalSumm;
	}

	/**
	 * @return Gets list of {@link Evaluation} extracted from parsed xml.
	 */
	public List<Evaluation> getEvaluations() {
		return new ArrayList<Evaluation>(this.evaluations);
	}

	/**
	 * @return Gets list of {@link Evaluation} for given textId.
	 */
	public List<Evaluation> getEvaluationsForText(int id) {

		List<Evaluation> textsEval = new ArrayList<Evaluation>();
		for (Evaluation eval : this.evaluations) {
			if (eval.getTextId() == id)
				textsEval.add(eval);
		}
		return textsEval;
	}

	/**
	 * Gets evaluations for user and for given text.
	 * 
	 * @param artId
	 * @param user
	 * @return
	 */
	public Evaluation getEvaluation(int artId, String user) {

		List<Evaluation> evalsForText = this.getEvaluationsForText(artId);
		List<Evaluation> evalsForUser = new ArrayList<Evaluation>();

		for (Evaluation eval : evalsForText) {
			if (eval.getUser().equals(user))
				evalsForUser.add(eval);
		}
		assert evalsForUser.size() > 1;
		if (evalsForUser.size() == 0)
			return null;
		return evalsForUser.get(0);

	}

	/**
	 * @return Gets list of {@link Article} taken from evaluations.xml.
	 */
	public Set<Integer> getArtIds() {
		Set<Integer> artIds = new HashSet<Integer>();
		for (Evaluation eval : this.evaluations) {
			artIds.add(eval.getTextId());
		}
		return artIds;
	}

	/**
	 * Validates {@link Evaluation}
	 * 
	 * @param evaluation
	 * @return
	 */
	private boolean validateEvaluation(Evaluation evaluation, Article article) {

		Integer id = evaluation.getTextId();
		if (id == null)
			return false;
		int length = this.summariesLength.get(id);
		if (length != evaluation.getChosen().length)
			return false;
		Set<Integer> parasNo = evaluation.getParagraphs();
		List<Paragraph> paragraphs = article.getParagraphs();
		try {
			for (Integer para : parasNo) {
				List<Sentence> sentences = paragraphs.get(para).getSentences();
				List<Integer> paraSentences = evaluation
						.getSentencesForPara(para);
				for (int sentence : paraSentences) {
					sentences.get(sentence);
				}
			}
		} catch (Exception e) {
			logger.info(" Error while validating text :"
					+ evaluation.getTextId() + " : " + evaluation.getUser()
					+ " " + Arrays.toString(evaluation.getChosen()));
			return false;
		}
		return true;
	}

}
