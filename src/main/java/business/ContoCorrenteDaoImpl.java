package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ContoCorrente;
import com.Movimento;

public class ContoCorrenteDaoImpl implements ContoCorrenteDao {

	List<ContoCorrente> conti = new ArrayList<>();

	@Override
	public ContoCorrente inserisci(ContoCorrente cc) {
		conti.add(cc);
		return cc;
	}

	@Override
	public ContoCorrente getByIban(String iban) {
		ContoCorrente c1 = new ContoCorrente();
		for (ContoCorrente cc : conti) {
			if (cc.getIban().equals(iban)) {
				c1 = cc;
			}
		}
		return c1;
	}

	@Override
	public void deleteCc(String iban) {
		for (ContoCorrente cc : conti) {
			if (cc.getIban().equals(iban)) {
				conti.remove(cc);
			}
		}
	}
/*
 * La tracci adiceva che bisognava creare un metodo per modificare il contocorrente, non specificando se dovesse
 * cambiare tutti i campi.
 * io  ho implementato tutto , ma a mio avviso la data e il saldo non dovrebbero essere cambviati da qui in quanto 
 * il saldo dovrebbe cambiare soltanto se viene fatto un prelievo o un versamento e la data di creazione del conto dovrebbe essere invariabile
 * l'iban dovrebbe poter cambiare solo nel momento in cui si cambia filiale.
 */
	@Override
	public ContoCorrente updateCc(ContoCorrente conto) {
		ContoCorrente contoUpdated = new ContoCorrente();
		for (ContoCorrente cc : conti) {
			if (cc.getIban().equals(conto.getIban())) {
				contoUpdated.setIntestatario(conto.getIntestatario());
				contoUpdated.setSaldo(conto.getSaldo());
				contoUpdated.setIban(conto.getIban());
				contoUpdated.setDate(conto.getDate());
			}
		}
		return contoUpdated;
	}

	@Override
	public ContoCorrente versamento(String iban, double versa) {
		ContoCorrente contoUpdated = new ContoCorrente();
		for (ContoCorrente cc : conti) {
			if (cc.getIban().equals(iban)) {
				double newSaldo = cc.getSaldo() + versa;
				contoUpdated.setSaldo(newSaldo);
				contoUpdated.nuovoMovimento("versamento");
			}
		}
		return contoUpdated;

	}

	@Override
	public ContoCorrente prelievo(String iban, double preleva) {
		ContoCorrente contoUpdated = new ContoCorrente();
		for (ContoCorrente cc : conti) {
			if (cc.getIban().equals(iban)) {
				double newSaldo = cc.getSaldo() - preleva;
				contoUpdated.setSaldo(newSaldo);
				contoUpdated.nuovoMovimento("prelievo");
			}
		}
		return contoUpdated;

	}

	@Override
	public List<Movimento> listaMovimenti(String iban) {
		ContoCorrente conto=getByIban(iban);
		return conto.getListaMovimenti();
		
	}

	
}
