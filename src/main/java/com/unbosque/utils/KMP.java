package com.unbosque.utils;

import java.util.ArrayList;
import java.util.List;

public class KMP {
	public int buscarCoincidencias(String archivo_txt, String palabra) {
		if (palabra == null || palabra.length() == 0) {
			System.out.println("La palabra esta en cero 0");
			return 0;
		}

		if (archivo_txt == null || palabra.length() > archivo_txt.length()) {
			System.out.println("Palabra no encontrada");
			return 0;
		}

		char[] chars = palabra.toCharArray();
		int[] next = new int[palabra.length() + 1];

		// Cálculo del array 'next' para optimizar la búsqueda
		for (int i = 1; i < palabra.length(); i++) {
			int j = next[i];

			while (j > 0 && chars[j] != chars[i]) {
				j = next[j];
			}

			if (j > 0 || chars[j] == chars[i]) {
				next[i + 1] = j + 1;
			}
		}

		List<String> foundWords = new ArrayList<>();
		List<Integer> startPositions = new ArrayList<>();
		List<Integer> endPositions = new ArrayList<>();
		int j = 0; // Índice del patrón
		for (int i = 0; i < archivo_txt.length(); i++) {
			if (j < palabra.length() && archivo_txt.charAt(i) == palabra.charAt(j)) {
				j++;
				if (j == palabra.length()) {

					// Coincidencia encontrada, agrega la palabra y sus posiciones de inicio y final
					String matchedWord = archivo_txt.substring(i - j + 1, i + 1);
					foundWords.add(matchedWord);
					startPositions.add(i - j + 1);
					endPositions.add(i);
					j = next[j];
				}
			} else if (j > 0) {
				j = next[j];
				i--;
			}
		}

		if (!foundWords.isEmpty()) {
			return foundWords.size();
		}

		return 0;
	}

}
