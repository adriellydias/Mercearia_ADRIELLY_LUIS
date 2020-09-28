package revisao2;

import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ModeloTabelaCompra extends AbstractTableModel {

    private Vector<Produto> carrinhoCompra;
    private CompraGUI painel;
    
    public ModeloTabelaCompra(CompraGUI painel){
        this.carrinhoCompra = new Vector<>();
        this.painel = painel;
    }
    
    @Override
    public int getRowCount() {
        return carrinhoCompra.size();
    }

    @Override
    public int getColumnCount() {
        //nome, preço, quantidade, total
        return 4;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        
        //objeto temporário devido as repetições de chamadas no código
        Produto temp = carrinhoCompra.get(linha);
        
        switch(coluna){
            case 0: return temp.getNome();
            case 1: return temp.getPreco();
            case 2: return temp.getQuantidade();
            case 3: return temp.getQuantidade() * temp.getPreco();
            default: return null;
        }
    }
    
    public void addNovoProduto (Produto vendido){
        this.carrinhoCompra.add(vendido);
    }

    public void removeProdutoCarrinho(int indice){
        this.carrinhoCompra.remove(indice);
    }
    
    @Override
    public String getColumnName(int coluna) {
        switch(coluna){
            case 0: return "NOME";
            case 1: return "PREÇO UNIT.";
            case 2: return "QUANTIDADE";
            case 3: return "PREÇO PARC.";
            default: return null;
        }
        
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        if(coluna == 2){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void setValueAt(Object novoValor, int linha, int coluna) {
        if(coluna == 2)
        {
            //requisitando senha para modificação em quant.
            String senha = JOptionPane.showInputDialog(null, "informe a senha", "operação restrita", JOptionPane.INFORMATION_MESSAGE);
            
            if(senha != null && senha.equals("ifmg")){
            
            carrinhoCompra.get(linha).setQuantidade((int)novoValor);
            
            //a tabela e valor da compra serão atualizados
            this.painel.atualizaQuantidades();
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch(coluna){
            case 0: return String.class;
            case 1: return Double.class;
            case 2: return Integer.class;
            case 3: return Double.class;
            default: return null;
        }
    }
    
    public double calculaPrecoParcialCompra(){
        double valor = 0.0;
        
        //recalculando valor do carrinho com produtos atuais.
        for(Produto p : carrinhoCompra){
            valor += p.getQuantidade() * p.getPreco();
        }
        return valor;
    }

    public Vector<Produto> produtosCarrinho() {
        return carrinhoCompra;
    }
    
    public void limpaCarrinho(){
        this.carrinhoCompra.clear();
    }
    
    
    
    
    
}
