import java.util.HashMap;

import java.util.Random;

/**

 * This is the main class for your Markov Model.

 *

 * Assume that the text will contain ASCII characters in the range [1,255].

 * ASCII character 0 (the NULL character) will be treated as a non-character.

 *

 * Any such NULL characters in the original text should be ignored.

 */

public class MarkovModel {

	// Use this to generate random numbers as needed

	private Random generator = new Random();

	// This is a special symbol to indicate no character

	public static final char NOCHARACTER = (char) 0;

	private int order;

	private HashMap<String, int[]> hashMap;

	/**

	 * Constructor for MarkovModel class.

	 *

	 * @param order the number of characters to identify for the Markov Model sequence

	 * @param seed the seed used by the random number generator

	 */

	public MarkovModel(int order, long seed) {

		// Initialize your class here

		this.order = order;

		// Initialize the random number generator

		generator.setSeed(seed);

	}

	/**

	 * Builds the Markov Model based on the specified text string.

	 */

	public void initializeText(String text) {

		// Build the Markov model here

		hashMap = new HashMap<>();

		for (int i = 0; i < text.length()- order; i++) {

			String temp = text.substring(i, i + order);

			if(hashMap.get(temp) == null) {

				hashMap.put(temp, new int[255]);

				int[] arr = hashMap.get(temp);

				for (int j = 0; j < arr.length; j++) {

					arr[j] = 0;

				}

				arr[text.charAt((i + order))]++;

			} else {

				int[] arr = hashMap.get(temp);

				arr[text.charAt((i + order))]++;

			}

		}

	}

	/**

	 * Returns the number of times the specified kgram appeared in the text.

	 */

	public int getFrequency(String kgram) {

		if(hashMap.get(kgram) == null) {

			return 0;

		} else {

			int [] arr = hashMap.get(kgram);

			int counter = 0;

			for (int i = 0; i < arr.length; i++) {

				counter += arr[i];

			}

			return counter;

		}

	}

	/**

	 * Returns the number of times the character c appears immediately after the specified kgram.

	 */

	public int getFrequency(String kgram, char c) {

		if(hashMap.get(kgram) == null) {

			return 0;

		} else {

			int [] arr = hashMap.get(kgram);

			return arr[c];

		}

	}

	/**

	 * Generates the next character from the Markov Model.

	 * Return NOCHARACTER if the kgram is not in the table, or if there is no

	 * valid character following the kgram.

	 */

	public char nextCharacter(String kgram) {

		// See the problem set description for details

		// on how to make the random selection.

		if(hashMap.get(kgram) == null) {

			return NOCHARACTER;

		} else {

			int[] arr = hashMap.get(kgram);

			int counter = 0;

			for (int i = 0; i < arr.length; i++) {

				counter += arr[i];

			}

			int generateNext = generator.nextInt(counter);

			for (int i = 0; i < arr.length; i++) {

				generateNext -= arr[i];

				if (generateNext < 0) {

					return (char) i;

				}

			}

			return NOCHARACTER;

		}

	}

}

