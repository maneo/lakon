#!/bin/ruby

#
# Generate gnuplot charts with the sentence number on the X axis
# and the number it was picked for a summary on the Y axis
#
def generate_sentence_selection_gnuplot(file, experiment, votes)
    file.puts "\n\n# chart for article: #{experiment}"

    stacked = {}

    votes.flatten.each do |sentence|
        stacked[sentence] = 0 unless stacked.has_key? sentence
        stacked[sentence] = stacked[sentence] + 1
    end

    for key in stacked.keys.sort
        file.puts sprintf("%3d  %3d\n", key, stacked[key])
    end
end


#
# Generates an 'overlap matrix' between users. Each cell in this
# matrix indicates the number of sentences two users had in common.
#
def generate_overlap_matrix(file, experiment, votes)
    file.puts "\n\nArticle: #{experiment}"

    for y in (0..votes.length - 1)
        file.print "   ," * y
        for x in (y..votes.length - 1)
            overlap = votes[y] & votes[x]          # set intersection
            file.print sprintf("%3d,", overlap.length)
        end
        file.puts
    end
end

#
# Process standard input (feed users-coverage.txt file)
#

user_votes = []
artId = nil

File.open("users-ovelap-for-texts-ruby.txt", "w") do |matrix_file|
    $stdin.each_line do |line|
        row = line.chomp!.split

        next unless row.length > 0

        if row[0] == "artId"
            unless artId.nil? or user_votes.empty?
                generate_overlap_matrix(matrix_file, artId, user_votes)

                File.open("sentence-selection-art-#{artId}", "w") do |f|
                    generate_sentence_selection_gnuplot(f, artId, user_votes)
                end
            end

            # reset experiment number.
            artId = row[2]
            user_votes = []
        else
            # add the vote
            user_votes.push row.map {|v| v.to_i }
        end
    end
end
