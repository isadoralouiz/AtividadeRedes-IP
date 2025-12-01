import java.util.Scanner;

public class CalcIP {

    final static Scanner LER = new Scanner(System.in);

    public static void main(String[] args) {

        String IP = lerIP();
        String[] partesEntrada = IP.split("/");
        String ip = partesEntrada[0];
        int maskBits = Integer.parseInt(partesEntrada[1]);

        String classe = classificarIP(ip);
        String enderecoRede = calcularEndRedeMask(ip, maskBits);
        String enderecoBroadcast = calcularEndBroadcastMask(ip, maskBits);
        String primeiroHost = calcularPrimeiroHost(enderecoRede);
        String ultimoHost = calcularUltimoHost(enderecoBroadcast);

        imprimir(ip, maskBits, classe, enderecoRede, enderecoBroadcast, primeiroHost, ultimoHost);
    }

    public static String lerIP() {
        System.out.print("Digite o endereço IP e máscara (Ex: xxx.xxx.xxx.xxx/xx): ");
        return LER.nextLine().trim();
    }

    public static String classificarIP(String ip) {
        String[] partes = ip.split("\\.");
        int num = Integer.parseInt(partes[0]);

        if (num >= 0 && num <= 127) {
            return "A";
        } else if (num >= 128 && num <= 191) {
            return "B";
        } else if (num >= 192 && num <= 223) {
            return "C";
        } else {
            return "Inválido";
        }
    }

    public static String calcularEndRedeMask(String ip, int maskBits) {
        String[] p = ip.split("\\.");
        int a = Integer.parseInt(p[0]);
        int b = Integer.parseInt(p[1]);
        int c = Integer.parseInt(p[2]);
        int d = Integer.parseInt(p[3]);

        if (maskBits <= 8) {
            int bloco = (int) Math.pow(2, 8 - maskBits);
            a = (a / bloco) * bloco;
            b = 0;
            c = 0;
            d = 0;

        } else if (maskBits <= 16) {
            int resto = maskBits - 8;
            int bloco = (int) Math.pow(2, 8 - resto);
            b = (b / bloco) * bloco;
            c = 0;
            d = 0;

        } else if (maskBits <= 24) {
            int resto = maskBits - 16;
            int bloco = (int) Math.pow(2, 8 - resto);
            c = (c / bloco) * bloco;
            d = 0;

        } else {
            int resto = maskBits - 24;
            int bloco = (int) Math.pow(2, 8 - resto);
            d = (d / bloco) * bloco;
        }

        return a + "." + b + "." + c + "." + d;
    }

    public static String calcularEndBroadcastMask(String ip, int maskBits) {
        String rede = calcularEndRedeMask(ip, maskBits);
        String[] p = rede.split("\\.");
        int a = Integer.parseInt(p[0]);
        int b = Integer.parseInt(p[1]);
        int c = Integer.parseInt(p[2]);
        int d = Integer.parseInt(p[3]);

        if (maskBits <= 8) {
            b = 255;
            c = 255;
            d = 255;

        } else if (maskBits <= 16) {
            c = 255;
            d = 255;

        } else if (maskBits <= 24) {
            d = 255;
        }

        int resto = maskBits % 8;
        if (resto != 0) {
            int bloco = (int) Math.pow(2, 8 - resto);

            if (maskBits <= 8) {
                a = a + (bloco - 1);
            } else if (maskBits <= 16) {
                b = b + (bloco - 1);
            } else if (maskBits <= 24) {
                c = c + (bloco - 1);
            } else {
                d = d + (bloco - 1);
            }
        }

        return a + "." + b + "." + c + "." + d;
    }

    public static String calcularPrimeiroHost(String rede) {
        String[] p = rede.split("\\.");
        int d = Integer.parseInt(p[3]) + 1;
        return p[0] + "." + p[1] + "." + p[2] + "." + d;
    }

    public static String calcularUltimoHost(String broadcast) {
        String[] p = broadcast.split("\\.");
        int d = Integer.parseInt(p[3]) - 1;
        return p[0] + "." + p[1] + "." + p[2] + "." + d;
    }

    public static void imprimir(String ip, int maskBits, String classe,
        String enderecoRede, String enderecoBroadcast, String faixaI, String faixaF) {

        System.out.println("\n--- Relatório ---");
        System.out.println("Endereço IP: " + ip + "/" + maskBits);
        System.out.println("Classe: " + classe);
        System.out.println("Endereço de Rede: " + enderecoRede);
        System.out.println("Endereço de Broadcast: " + enderecoBroadcast);
        System.out.println("Faixa de Hosts: " + faixaI + " a " + faixaF);
    }
}
