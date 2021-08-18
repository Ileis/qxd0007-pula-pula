import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PulaPula {

    private int limiteMax;
    private List<Crianca> filaDeEspera = new LinkedList<>();
    private List<Crianca> pulaPula = new LinkedList<>();
    private double caixa = 0;
    private HashMap<String, Double> conta = new HashMap<>();
    private double VALOR_DE_ENTRADA = 2.50;


    public PulaPula(int limiteMax){
        this.limiteMax = limiteMax;
    }

    public List<Crianca> getFilaDeEspera() {
        return filaDeEspera;
    }

    public List<Crianca> getCriancasPulando() {
        return pulaPula;
    }

    public int getLimiteMax() {
        return limiteMax;
    }

    public double getCaixa() {
        return caixa;
    }

    public Double getConta(String name){
        return conta.get(name);
    }

    public boolean entrarNaFila(Crianca crianca){

        if(conta.containsKey(crianca.getName()))
            return false;

        conta.put(crianca.getName(), null);
        filaDeEspera.add(crianca);

        return true;
    }

    public boolean entrar(){
        if(pulaPula.size() < limiteMax && !filaDeEspera.isEmpty()){

            if(conta.get(filaDeEspera.get(0).getName()) == null)
                conta.put(filaDeEspera.get(0).getName(), VALOR_DE_ENTRADA);
            else
                conta.put(filaDeEspera.get(0).getName(), conta.get(filaDeEspera.get(0).getName()) + VALOR_DE_ENTRADA);

            pulaPula.add(filaDeEspera.get(0));
            filaDeEspera.remove(0);

            return true;
        }
        return false;
    }

    public boolean sair(){
        if(!pulaPula.isEmpty()){
            filaDeEspera.add(pulaPula.get(0));
            pulaPula.remove(0);

            return true;
        }
        return false;
    }

    public boolean papaiChegou(String name){
        if(!filaDeEspera.isEmpty() || !pulaPula.isEmpty()){

            boolean achou = false;

            for(Crianca aux : filaDeEspera){
                if(aux.getName().equals(name)){
                    filaDeEspera.remove(aux);
                    achou = true;
                    break;
                }
            }

            if(!achou){
                for(Crianca aux : pulaPula){
                    if(aux.getName().equals(name)){
                        pulaPula.remove(aux);
                        achou = true;
                        break;
                    }
                }
            }

            if(!achou)
                return false;

            if(conta.get(name) != null)
                caixa += conta.get(name);
            conta.remove(name);

            return true;

        }
        return false;
    }

    public double fechar(){

        double totalValue = 0;

        for(Crianca aux : filaDeEspera)
            if(conta.get(aux.getName()) != null)
                totalValue += conta.get(aux.getName());

        filaDeEspera.clear();

        for(Crianca aux : pulaPula)
            if(conta.get(aux.getName()) != null)
                totalValue += conta.get(aux.getName());

        pulaPula.clear();
        conta.clear();

        caixa += totalValue;

        return totalValue;
    }
}