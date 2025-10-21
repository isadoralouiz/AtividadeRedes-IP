import java.util.Scanner;

public class verificarIP {

    final static Scanner LER = new Scanner(System.in);

    public static void main(String[] args) {

        String ip = lerIP();

        String classe = classificarIP(ip);
        String enderecoRede = calcularEndRede(ip, classe);
        String enderecoBroadcast = calcularEndBroadcast(ip, classe);
        String faixaI = calcularFaixaI(enderecoRede);
        String faixaF = calcularFaixaF(enderecoBroadcast);

        imprimir(ip, classe, enderecoRede, enderecoBroadcast, faixaI, faixaF);
    }

    public static String lerIP() {
        System.out.print("Digite um endereço IP no formato xxx.xxx.xxx.xxx: ");
        String x = LER.nextLine().trim();
        return x;
    }

    public static String classificarIP(String ip) {
        String classe;
        String[] partes = ip.split("\\.");
        int num = Integer.parseInt(partes[0]);

        if (num >= 0 && num <= 127) {
            classe = "A";
        } else if (num >= 128 && num <= 191) {
            classe = "B";
        } else if (num >= 192 && num <= 223) {
            classe = "C";
        } else {
            classe = "Inválido";
        }
        return classe;
    }

    public static String calcularEndRede(String ip, String classe) {
        String enderecoRede;
        String[] partes = ip.split("\\.");

        if (classe.equals("A")) {
            enderecoRede = partes[0] + ".0.0.0";
        } else if (classe.equals("B")) {
            enderecoRede = partes[0] + "." + partes[1] + ".0.0";
        } else if (classe.equals("C")) {
            enderecoRede = partes[0] + "." + partes[1] + "." + partes[2] + ".0";
        } else {
            enderecoRede = "Inválido";
        }

        return enderecoRede;
    }

    public static String calcularEndBroadcast(String ip, String classe) {
        String enderecoBroadcast;
        String[] partes = ip.split("\\.");

        if (classe.equals("A")) {
            enderecoBroadcast = partes[0] + ".255.255.255";
        } else if (classe.equals("B")) {
            enderecoBroadcast = partes[0] + "." + partes[1] + ".255.255";
        } else if (classe.equals("C")) {
            enderecoBroadcast = partes[0] + "." + partes[1] + "." + partes[2] + ".255";
        } else {
            enderecoBroadcast = "Inválido";
        }

        return enderecoBroadcast;
    }

    public static String calcularFaixaI(String enderecoRede) {
        String[] partes = enderecoRede.split("\\.");
        return partes[0] + "." + partes[1] + "." + partes[2] + "." + (Integer.parseInt(partes[3]) + 1);
    }

    public static String calcularFaixaF(String enderecoBroadcast) {
        String[] partes = enderecoBroadcast.split("\\.");
        return partes[0] + "." + partes[1] + "." + partes[2] + "." + (Integer.parseInt(partes[3]) - 1);
    }

    public static void imprimir(String ip, String classe, String enderecoRede, String enderecoBroadcast, String faixaI, String faixaF) {
        System.out.println("\n--- Relatório ---");
        System.out.println("Endereço IP: " + ip);
        System.out.println("Classe: " + classe);
        System.out.println("Endereço de Rede: " + enderecoRede);
        System.out.println("Endereço de Broadcast: " + enderecoBroadcast);
        System.out.println("Faixa de Hosts: " + faixaI + " a " + faixaF);
    }
}
