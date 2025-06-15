import java.util.*;

public class MagicTranslator {

    static class Symbol {
        String symbol;
        String phoneme;
        Symbol(String symbol, String phoneme) {
            this.symbol = symbol;
            this.phoneme = phoneme;
        }
    }

    static final Map<Character, Symbol> MAGIC_ALPHABET = new HashMap<>();
    static final Map<String, Character> REVERSE_MAGIC_ALPHABET = new HashMap<>();

    static {
        MAGIC_ALPHABET.put('A', new Symbol("𐐅", "to"));
        MAGIC_ALPHABET.put('B', new Symbol("𐐆", "ta"));
        MAGIC_ALPHABET.put('C', new Symbol("𐐇", "nes"));
        MAGIC_ALPHABET.put('Ç', new Symbol("😔", "não"));
        MAGIC_ALPHABET.put('D', new Symbol("𐐈", "nas"));
        MAGIC_ALPHABET.put('E', new Symbol("𐐉", "nos"));
        MAGIC_ALPHABET.put('F', new Symbol("𐐊", "tes"));
        MAGIC_ALPHABET.put('G', new Symbol("𐐋", "la"));
        MAGIC_ALPHABET.put('H', new Symbol("𐐌", "me"));
        MAGIC_ALPHABET.put('I', new Symbol("𐐍", "mo"));
        MAGIC_ALPHABET.put('J', new Symbol("𐐎", "ma"));
        MAGIC_ALPHABET.put('K', new Symbol("𐐏", "le"));
        MAGIC_ALPHABET.put('L', new Symbol("𐐐", "lo"));
        MAGIC_ALPHABET.put('M', new Symbol("𐐑", "sa"));
        MAGIC_ALPHABET.put('N', new Symbol("£", "se"));
        MAGIC_ALPHABET.put('O', new Symbol("𐐓", "so"));
        MAGIC_ALPHABET.put('P', new Symbol("𐐔", "ba"));
        MAGIC_ALPHABET.put('Q', new Symbol("€", "be"));
        MAGIC_ALPHABET.put('R', new Symbol("𐐖", "bo"));
        MAGIC_ALPHABET.put('S', new Symbol("𐐗", "sko"));
        MAGIC_ALPHABET.put('T', new Symbol("𐐘", "ska"));
        MAGIC_ALPHABET.put('U', new Symbol("𐐙", "pa"));
        MAGIC_ALPHABET.put('V', new Symbol("𐐚", "po"));
        MAGIC_ALPHABET.put('W', new Symbol("%", "pe"));
        MAGIC_ALPHABET.put('X', new Symbol("𐐜", "chi"));
        MAGIC_ALPHABET.put('Y', new Symbol("𐐝", "cho"));
        MAGIC_ALPHABET.put('Z', new Symbol("𐐞", "cha"));

        for (Map.Entry<Character, Symbol> e : MAGIC_ALPHABET.entrySet()) {
            REVERSE_MAGIC_ALPHABET.put(e.getValue().symbol, e.getKey());
        }
    }

    static boolean isVowel(char c) {
        return "AEIOUYaeiouy".indexOf(c) >= 0;
    }

    static List<String> dividirSilabas(String palavra) {
        List<String> resultado = new ArrayList<>();
        StringBuilder silaba = new StringBuilder();
        for (int i = 0; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            silaba.append(c);
            if (isVowel(c)) {
                if (i + 1 == palavra.length()) {
                    resultado.add(silaba.toString());
                    silaba.setLength(0);
                } else if (!isVowel(palavra.charAt(i + 1))) {
                    silaba.append(palavra.charAt(i + 1));
                    i++;
                    if (i + 1 == palavra.length() || isVowel(palavra.charAt(i + 1))) {
                        resultado.add(silaba.toString());
                        silaba.setLength(0);
                    }
                } else {
                    resultado.add(silaba.toString());
                    silaba.setLength(0);
                }
            }
        }
        if (silaba.length() > 0) {
            resultado.add(silaba.toString());
        }
        return resultado;
    }

    static String inverterSilaba(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    static class TranslationResult {
        String magicText;
        String phonemes;
        String description;
        TranslationResult(String magicText, String phonemes, String description) {
            this.magicText = magicText;
            this.phonemes = phonemes;
            this.description = description;
        }
    }

    public static TranslationResult latimParaMagico(String fraseLatim) {
        String[] palavras = fraseLatim.split("\\s+");
        List<String> palavrasInvertidas = new ArrayList<>();
        StringBuilder desc = new StringBuilder();
        for (String palavra : palavras) {
            List<String> silabas = dividirSilabas(palavra);
            desc.append("\nDivisão de sílabas de '").append(palavra).append("': ").append(silabas).append("\n");
            List<String> invertidas = new ArrayList<>();
            for (String s : silabas) invertidas.add(inverterSilaba(s));
            desc.append("Sílabas invertidas: ").append(invertidas).append("\n");
            palavrasInvertidas.add(String.join(".", invertidas));
        }
        Collections.reverse(palavrasInvertidas);
        String fraseInvertida = String.join(" ", palavrasInvertidas);
        desc.append("\nFrase invertida: ").append(fraseInvertida).append("\n");

        StringBuilder magic = new StringBuilder();
        StringBuilder phonemes = new StringBuilder();
        StringBuilder palavraFonemas = new StringBuilder();

        for (int i = 0; i < fraseInvertida.length();) {
            int cp = fraseInvertida.codePointAt(i);
            String ch = new String(Character.toChars(cp));
            i += Character.charCount(cp);
            if (ch.equals(" ")) {
                magic.append(" ");
                phonemes.append(palavraFonemas.toString().trim()).append(" ");
                palavraFonemas.setLength(0);
            } else if (ch.equals(".")) {
                magic.append(".");
            } else {
                char upper = ch.toUpperCase().charAt(0);
                Symbol sym = MAGIC_ALPHABET.get(upper);
                if (sym != null) {
                    magic.append(sym.symbol);
                    palavraFonemas.append(sym.phoneme);
                } else {
                    magic.append(ch);
                }
            }
        }
        phonemes.append(palavraFonemas.toString().trim());
        return new TranslationResult(magic.toString(), phonemes.toString(), desc.toString());
    }

    public static String magicoParaLatim(String fraseMagica) {
        StringBuilder fraseInvertida = new StringBuilder();
        for (int i = 0; i < fraseMagica.length();) {
            int cp = fraseMagica.codePointAt(i);
            String ch = new String(Character.toChars(cp));
            i += Character.charCount(cp);
            Character lat = REVERSE_MAGIC_ALPHABET.get(ch);
            if (lat != null) {
                fraseInvertida.append(lat);
            } else {
                fraseInvertida.append(ch);
            }
        }
        String[] palavrasInv = fraseInvertida.toString().split("\\s+");
        List<String> palavras = new ArrayList<>();
        for (String pInv : palavrasInv) {
            String[] silabasInv = pInv.split("\\.");
            List<String> silabas = new ArrayList<>();
            for (String s : silabasInv) silabas.add(inverterSilaba(s));
            palavras.add(String.join("", silabas));
        }
        Collections.reverse(palavras);
        return String.join(" ", palavras);
    }

    public static void main(String[] args) {
        String frase = "Salve mundi";
        TranslationResult res = latimParaMagico(frase);
        System.out.println("Magico: " + res.magicText);
        System.out.println("Fonemas: " + res.phonemes);
        System.out.println(res.description);
        String original = magicoParaLatim(res.magicText);
        System.out.println("Latim novamente: " + original);
    }
}

