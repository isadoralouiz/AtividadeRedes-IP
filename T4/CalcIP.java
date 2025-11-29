import java.util.Scanner;

public class CalcIP {

    final static Scanner LER = new Scanner(System.in);

    public static void main(String[] args) {

        String entrada = lerIP(); // IP/MR

        String[] partes = entrada.split("/");
        String ip = partes[0];
        int maskBits = Integer.parseInt(partes[1]);

        String enderecoRede = calcularEndRede(ip, maskBits);
        String enderecoBroadcast = calcularEndBroadcast(ip, maskBits);
        String primeiroHost = calcularPrimeiroHost(enderecoRede);
        String ultimoHost = calcularUltimoHost(enderecoBroadcast);

        imprimir(enderecoRede, enderecoBroadcast, primeiroHost, ultimoHost);
    }

    public static String lerIP() {
        System.out.print("Digite o endereço IP e máscara (Ex: 192.168.248.250/24): ");
        return LER.nextLine().trim();
    }

    public static String calcularEndRede(String ip, int maskBits) {
        int[] ipOctetos = parseIP(ip);
        int[] mascaraOctetos = gerarMascara(maskBits);
        int[] redeOctetos = new int[4];

        for (int i = 0; i < 4; i++) {
            redeOctetos[i] = ipOctetos[i] & mascaraOctetos[i];
        }

        return formatIP(redeOctetos);
    }

    public static String calcularEndBroadcast(String ip, int maskBits) {
        int[] ipOctetos = parseIP(ip);
        int[] mascaraOctetos = gerarMascara(maskBits);
        int[] broadcastOctetos = new int[4];

        for (int i = 0; i < 4; i++) {
            broadcastOctetos[i] = ipOctetos[i] | (~mascaraOctetos[i] & 0xFF);
        }

        return formatIP(broadcastOctetos);
    }

    public static String calcularPrimeiroHost(String enderecoRede) {
        int[] octetos = parseIP(enderecoRede);
        octetos[3] += 1;
        return formatIP(octetos);
    }

    public static String calcularUltimoHost(String enderecoBroadcast) {
        int[] octetos = parseIP(enderecoBroadcast);
        octetos[3] -= 1;
        return formatIP(octetos);
    }

    // Métodos auxiliares
    private static int[] parseIP(String ip) {
        String[] partes = ip.split("\\.");
        int[] octetos = new int[4];
        for (int i = 0; i < 4; i++) {
            octetos[i] = Integer.parseInt(partes[i]);
        }
        return octetos;
    }

    private static int[] gerarMascara(int maskBits) {
        int[] mascaraOctetos = new int[4];
        for (int i = 0; i < 4; i++) {
            if (maskBits >= 8) {
                mascaraOctetos[i] = 255;
                maskBits -= 8;
            } else {
                mascaraOctetos[i] = (int)(256 - Math.pow(2, 8 - maskBits));
                maskBits = 0;
            }
        }
        return mascaraOctetos;
    }

    private static String formatIP(int[] octetos) {
        return octetos[0] + "." + octetos[1] + "." + octetos[2] + "." + octetos[3];
    }

    public static void imprimir(String enderecoRede, String enderecoBroadcast, String primeiroHost, String ultimoHost) {
        System.out.println("\nRede: " + enderecoRede);
        System.out.println("Broadcast: " + enderecoBroadcast);
        System.out.println("Hosts: de " + primeiroHost + " a " + ultimoHost);
    }
}
