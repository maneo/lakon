package org.grejpfrut.tiller.utils;

import java.lang.reflect.Constructor;

/**
 * Base class tool reflction helper.
 * 
 * @author Adam Dudczak
 * 
 */
public class ClassTool {

	/**
	 * Gets object by its {@link Constructor}.
	 * 
	 * @param fullClassName -
	 *            full class name.
	 * @param params -
	 *            an array of params, for constructor.
	 * @return new instance of desired class.
	 */
	public static Object getInstance(String fullClassName, Object[] params) {

		Class cleaner;
		try {
			cleaner = Thread.currentThread().getContextClassLoader().loadClass(
					fullClassName);
			Constructor constructor = cleaner
					.getConstructor(new Class[] { TillerConfiguration.class });
			return constructor.newInstance(params);

		} catch (Exception e) {
			throw new RuntimeException("Exception while getting instance of "
					+ fullClassName, e);
		}

	}

	/**
	 * Gets object with its default constructor.
	 * 
	 * @param fullClassName -
	 *            full class name.
	 * @return new instance of desired class.
	 */
	public static Object getInstance(String fullClassName) {

		Class cleaner;
		try {
			cleaner = Thread.currentThread().getContextClassLoader().loadClass(
					fullClassName);
			return cleaner.newInstance();

		} catch (Exception e) {
			throw new RuntimeException("Exception while getting instance of "
					+ fullClassName, e);
		}

	}

}
