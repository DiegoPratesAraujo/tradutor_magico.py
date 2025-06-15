public class MagicTranslator {
    private static final java.util.Map<Character, String> MAGIC_ALPHABET = new java.util.LinkedHashMap<>();
    private static final java.util.Map<Character, String> MAGIC_PHONEMES = new java.util.LinkedHashMap<>();
    private static final java.util.Map<String, Character> REVERSE_MAGIC_ALPHABET = new java.util.HashMap<>();
    static {
        MAGIC_ALPHABET.put('A', "𐐅");
        MAGIC_PHONEMES.put('A', "to");
        MAGIC_ALPHABET.put('B', "𐐆");
        MAGIC_PHONEMES.put('B', "ta");
        MAGIC_ALPHABET.put('C', "𐐇");
        MAGIC_PHONEMES.put('C', "nes");
        MAGIC_ALPHABET.put('Ç', "😔");
        MAGIC_PHONEMES.put('Ç', "não");
        MAGIC_ALPHABET.put('D', "𐐈");
        MAGIC_PHONEMES.put('D', "nas");
        MAGIC_ALPHABET.put('E', "𐐉");
        MAGIC_PHONEMES.put('E', "nos");
        MAGIC_ALPHABET.put('F', "𐐊");
        MAGIC_PHONEMES.put('F', "tes");
        MAGIC_ALPHABET.put('G', "𐐋");
        MAGIC_PHONEMES.put('G', "la");
        MAGIC_ALPHABET.put('H', "𐐌");
        MAGIC_PHONEMES.put('H', "me");
        MAGIC_ALPHABET.put('I', "𐐍");
        MAGIC_PHONEMES.put('I', "mo");
        MAGIC_ALPHABET.put('J', "𐐎");
        MAGIC_PHONEMES.put('J', "ma");
        MAGIC_ALPHABET.put('K', "𐐏");
        MAGIC_PHONEMES.put('K', "le");
        MAGIC_ALPHABET.put('L', "𐐐");
        MAGIC_PHONEMES.put('L', "lo");
        MAGIC_ALPHABET.put('M', "𐐑");
        MAGIC_PHONEMES.put('M', "sa");
        MAGIC_ALPHABET.put('N', "£");
        MAGIC_PHONEMES.put('N', "se");
        MAGIC_ALPHABET.put('O', "𐐓");
        MAGIC_PHONEMES.put('O', "so");
        MAGIC_ALPHABET.put('P', "𐐔");
        MAGIC_PHONEMES.put('P', "ba");
        MAGIC_ALPHABET.put('Q', "€");
        MAGIC_PHONEMES.put('Q', "be");
        MAGIC_ALPHABET.put('R', "𐐖");
        MAGIC_PHONEMES.put('R', "bo");
        MAGIC_ALPHABET.put('S', "𐐗");
        MAGIC_PHONEMES.put('S', "sko");
        MAGIC_ALPHABET.put('T', "𐐘");
        MAGIC_PHONEMES.put('T', "ska");
        MAGIC_ALPHABET.put('U', "𐐙");
        MAGIC_PHONEMES.put('U', "pa");
        MAGIC_ALPHABET.put('V', "𐐚");
        MAGIC_PHONEMES.put('V', "po");
        MAGIC_ALPHABET.put('W', "%");
        MAGIC_PHONEMES.put('W', "pe");
        MAGIC_ALPHABET.put('X', "𐐜");
        MAGIC_PHONEMES.put('X', "chi");
        MAGIC_ALPHABET.put('Y', "𐐝");
        MAGIC_PHONEMES.put('Y', "cho");
        MAGIC_ALPHABET.put('Z', "𐐞");
        MAGIC_PHONEMES.put('Z', "cha");
        for (java.util.Map.Entry<Character, String> e : MAGIC_ALPHABET.entrySet()) {
            REVERSE_MAGIC_ALPHABET.put(e.getValue(), e.getKey());
        }
    }

    public static class TranslationResult {
        public final String text;
        public final String phonemes;
        public final String description;
        public TranslationResult(String text, String phonemes, String description) {
            this.text = text;
            this.phonemes = phonemes;
            this.description = description;
        }
    }

    private static java.util.List<String> dividirSilabas(String palavra) {
        String vogais = "AEIOUYaeiouy";
        java.util.List<String> resultado = new java.util.ArrayList<>();
        StringBuilder silaba = new StringBuilder();
        int i = 0;
        while (i < palavra.length()) {
            char c = palavra.charAt(i);
            silaba.append(c);
            if (vogais.indexOf(c) >= 0) {
                if (i + 1 == palavra.length()) {
                    resultado.add(silaba.toString());
                    silaba.setLength(0);
                } else if (vogais.indexOf(palavra.charAt(i + 1)) < 0) {
                    silaba.append(palavra.charAt(i + 1));
                    i += 1;
                    if (i + 1 == palavra.length() || vogais.indexOf(palavra.charAt(i + 1)) >= 0) {
                        resultado.add(silaba.toString());
                        silaba.setLength(0);
                    }
                } else {
                    resultado.add(silaba.toString());
                    silaba.setLength(0);
                }
            }
            i += 1;
        }
        if (silaba.length() > 0) {
            resultado.add(silaba.toString());
        }
        return resultado;
    }

    private static String inverterSilaba(String silaba) {
        return new StringBuilder(silaba).reverse().toString();
    }

    public static TranslationResult latimParaMagico(String fraseLatim) {
        String[] palavras = fraseLatim.split("\\s+");
        java.util.List<String> palavrasInvertidas = new java.util.ArrayList<>();
        StringBuilder desc = new StringBuilder();
        for (String palavra : palavras) {
            java.util.List<String> silabas = dividirSilabas(palavra);
            desc.append("\nDivisão de sílabas de '").append(palavra).append("': ").append(silabas).append("\n");
            java.util.List<String> silabasInv = new java.util.ArrayList<>();
            for (String s : silabas) {
                silabasInv.add(inverterSilaba(s));
            }
            desc.append("Sílabas invertidas: ").append(silabasInv).append("\n");
            palavrasInvertidas.add(String.join(".", silabasInv));
        }
        java.util.Collections.reverse(palavrasInvertidas);
        String fraseInvertida = String.join(" ", palavrasInvertidas);
        desc.append("\nFrase invertida: ").append(fraseInvertida).append("\n");
        StringBuilder fraseMagica = new StringBuilder();
        StringBuilder phonemes = new StringBuilder();
        StringBuilder palavraFonemas = new StringBuilder();
        String upper = fraseInvertida.toUpperCase();
        for (int idx = 0; idx < upper.length(); idx++) {
            char ch = upper.charAt(idx);
            if (ch == ' ') {
                fraseMagica.append(' ');
                if (palavraFonemas.length() > 0) {
                    phonemes.append(palavraFonemas.toString().trim()).append(' ');
                    palavraFonemas.setLength(0);
                } else {
                    phonemes.append(' ');
                }
            } else if (MAGIC_ALPHABET.containsKey(ch)) {
                fraseMagica.append(MAGIC_ALPHABET.get(ch));
                palavraFonemas.append(MAGIC_PHONEMES.get(ch));
            } else {
                fraseMagica.append(ch);
            }
        }
        if (palavraFonemas.length() > 0) {
            phonemes.append(palavraFonemas.toString().trim());
        }
        return new TranslationResult(fraseMagica.toString(), phonemes.toString().trim(), desc.toString());
    }

    public static TranslationResult magicoParaLatim(String fraseMagica) {
        StringBuilder fraseInvertida = new StringBuilder();
        for (int offset = 0; offset < fraseMagica.length(); ) {
            int cp = fraseMagica.codePointAt(offset);
            String ch = new String(Character.toChars(cp));
            if (REVERSE_MAGIC_ALPHABET.containsKey(ch)) {
                fraseInvertida.append(REVERSE_MAGIC_ALPHABET.get(ch));
            } else {
                fraseInvertida.append(ch);
            }
            offset += Character.charCount(cp);
        }
        StringBuilder desc = new StringBuilder();
        desc.append("Frase invertida: ").append(fraseInvertida).append("\n");
        String[] palavrasInv = fraseInvertida.toString().trim().split("\\s+");
        java.util.List<String> palavras = new java.util.ArrayList<>();
        for (String pInv : palavrasInv) {
            String[] silabasInv = pInv.split("\\.");
            StringBuilder palavra = new StringBuilder();
            for (String sInv : silabasInv) {
                palavra.append(inverterSilaba(sInv));
            }
            palavras.add(palavra.toString());
        }
        java.util.Collections.reverse(palavras);
        String fraseLatim = String.join(" ", palavras);
        desc.append("Frase em latim: ").append(fraseLatim).append("\n");
        return new TranslationResult(fraseLatim, "", desc.toString());
    }

    public static void main(String[] args) {
        String input = String.join(" ", args);
        if (input.isEmpty()) {
            input = "Salve mundi";
        }
        TranslationResult res = latimParaMagico(input);
        System.out.println(res.text);
        System.out.println(res.phonemes);
        System.out.println(magicoParaLatim(res.text).text);
    }
}
