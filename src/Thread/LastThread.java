package Thread;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingUtilities;

import Model.Model;
import Search.SearchATC;
import Search.SearchStitch;
import Search.Sider;

public class LastThread extends Thread {

	Model model;
	OrphaThread thread1;
	HpoThread thread2;
	OmimThread thread3;
	SiderThread thread4;
	public LastThread(Model m,OrphaThread th1,HpoThread th2,OmimThread th3,SiderThread th4)
	{
		model = m;
		thread1 = th1;
		thread2 = th2;
		thread3 = th3;
		thread4 = th4;
	}
	@Override
	public void run() 
	{
		Date start = new Date();
	    while(thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive());
	    Date end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " Wait milliseconds");
	    ArrayList<String> DrugLabels = thread4.Labels;
		ArrayList<ArrayList<String>> presquefin = combin(thread1.CUIandDiseaseOrpha,thread2.DiseaseANDcui, thread3.CUIandDiseaseOmim);
    	presquefin = trie(presquefin);
    	ArrayList<ArrayList<String>> AllStitchID = Sider.GetStitchIDfromCUI(presquefin);
    	ArrayList<ArrayList<String>> ATC = SearchStitch.SearchStitchAll(AllStitchID);
		ArrayList<ArrayList<String>> Labels = SearchATC.SearchATC(ATC);
	    end = new Date();
	    System.out.println(end.getTime() - start.getTime() + " Total milliseconds");
	    affichemedoc(Labels);
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            model.sendResult(Labels, DrugLabels);
	        }
	    });
	}
	public static void affichemedoc(ArrayList<ArrayList<String>> l ){
		for(int i = 0;i<l.size();i++){
			for(int j = 3 ;j<l.get(i).size();j++){
				System.out.println(l.get(i).get(j));
			}
		}
	}
	 public static ArrayList<ArrayList<String>> combin(ArrayList<ArrayList<String>> Orpha,ArrayList<ArrayList<String>> HPO ,ArrayList<ArrayList<String>> Omim){
		 ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>> ();
		 int cpt = 0;
		 boolean BOrpha = true;
		 boolean BHPO = false;
		 boolean BOmim = false;
		 for(int i = 0; i < Orpha.size();i++){
			if(ContainsMaladie(res,Orpha.get(i).get(0)) != -2){
				String sAajouter = res.get((ContainsMaladie(res,Orpha.get(i).get(0)))).get(1);
				int score = 1;
				
				if (!sAajouter.contains("Orpha")&&BOrpha){
					
					sAajouter = sAajouter + "," + "Orpha";
					score++;
				}
				
				
				if (!sAajouter.contains("HPO")&&BHPO){
					sAajouter = sAajouter + "," + "HPO";
					score++;
					
				}

				if (!sAajouter.contains("Omim")&&BOmim){
		
					sAajouter = sAajouter + "," + "Omim";
					score++;
				}
				
				
				
				
				res.get((ContainsMaladie(res,Orpha.get(i).get(0)))).set(1, sAajouter);
				
				res.get((ContainsMaladie(res,Orpha.get(i).get(0)))).set(2,String.valueOf(score) );
				for(int j = 1; j<Orpha.get(i).size();j++){
					if(!ContainsCui(res,Orpha.get(i).get(j))){
						res.get(ContainsMaladie(res,Orpha.get(i).get(0))).add(res.get(ContainsMaladie(res,Orpha.get(i).get(0))).size(),Orpha.get(i).get(j));
					}
				}
				
			}
			
			
			else{
				ArrayList<String> aAjoute = new ArrayList<String>();
				aAjoute.add(0,Orpha.get(i).get(0));
				aAjoute.add(1,"Orpha");
				aAjoute.add(2,"1");
				
				for(int j =3 ; j < Orpha.get(i).size();j++){
					aAjoute.add(j,Orpha.get(i).get(j));
					
				}
				res.add(cpt,aAjoute);
				cpt++;
			}
	    		
	    }
		 
		  BOrpha = false;
		  BHPO = true;
		  BOmim = false;
		
		  for(int i = 0; i < HPO.size();i++){
				if(ContainsMaladie(res,HPO.get(i).get(0)) != -2){
					String sAajouter = res.get((ContainsMaladie(res,HPO.get(i).get(0)))).get(1);
					int score = 1;
					
					if (!sAajouter.contains("Orpha")&&BOrpha){
						
						sAajouter = sAajouter + "," + "Orpha";
						score++;
					}
					
					
					if (!sAajouter.contains("HPO")&&BHPO){
						sAajouter = sAajouter + "," + "HPO";
						score++;
						
					}

					if (!sAajouter.contains("Omim")&&BOmim){
			
						sAajouter = sAajouter + "," + "Omim";
						score++;
					}
					
					
					
					
					res.get((ContainsMaladie(res,HPO.get(i).get(0)))).set(1, sAajouter);
					
					res.get((ContainsMaladie(res,HPO.get(i).get(0)))).set(2,String.valueOf(score) );
					for(int j = 1; j<HPO.get(i).size();j++){
						if(!ContainsCui(res,HPO.get(i).get(j))){
							res.get(ContainsMaladie(res,HPO.get(i).get(0))).add(res.get(ContainsMaladie(res,HPO.get(i).get(0))).size(),HPO.get(i).get(j));
						}
					}
					
				}
				
				
				else{
					ArrayList<String> aAjoute = new ArrayList<String>();
					aAjoute.add(0,HPO.get(i).get(0));
					aAjoute.add(1,"HPO");
					aAjoute.add(2,"1");
					
					for(int j =3 ; j < HPO.get(i).size();j++){
						aAjoute.add(j,HPO.get(i).get(j));
						
					}
					res.add(cpt,aAjoute);
					cpt++;
				}
		    		
		    }
		  
		  BOrpha = false;
		  BHPO = false;
		  BOmim = true;
		  
		  for(int i = 0; i < Omim.size();i++){
				if(ContainsMaladie(res,Omim.get(i).get(0)) != -2){
					String sAajouter = res.get((ContainsMaladie(res,Omim.get(i).get(0)))).get(1);
					int score = 1;
					
					if (!sAajouter.contains("Orpha")&&BOrpha){
						
						sAajouter = sAajouter + "," + "Orpha";
						score++;
					}
					
					
					if (!sAajouter.contains("HPO")&&BHPO){
						sAajouter = sAajouter + "," + "HPO";
						score++;
						
					}

					if (!sAajouter.contains("Omim")&&BOmim){
			
						sAajouter = sAajouter + "," + "Omim";
						score++;
					}
					
					
					
					
					res.get((ContainsMaladie(res,Omim.get(i).get(0)))).set(1, sAajouter);
					
					res.get((ContainsMaladie(res,Omim.get(i).get(0)))).set(2,String.valueOf(score) );
					for(int j = 1; j<Omim.get(i).size();j++){
						if(!ContainsCui(res,Omim.get(i).get(j))){
							res.get(ContainsMaladie(res,Omim.get(i).get(0))).add(res.get(ContainsMaladie(res,Omim.get(i).get(0))).size(),Omim.get(i).get(j));
						}
					}
					
				}
				
				
				else{
					ArrayList<String> aAjoute = new ArrayList<String>();
					aAjoute.add(0,Omim.get(i).get(0));
					aAjoute.add(1,"Omim");
					aAjoute.add(2,"1");
					
					for(int j =3 ; j < Omim.get(i).size();j++){
						aAjoute.add(j,Omim.get(i).get(j));
						
					}
					res.add(cpt,aAjoute);
					cpt++;
				}
		    		
		    }
		
		 
		 
		 return res;
	    }
	 public static int ContainsMaladie( ArrayList<ArrayList<String>> list, String value)
	    {
			int res = -2;
			
	        for(int i = 0; i < list.size(); i++)
	        {
	            if(list.get(i).get(0).equals(value))
	            	res = i;
	                return res;
	        }
	        return res;
	    }
		
		public static boolean ContainsCui( ArrayList<ArrayList<String>> list, String value)
	    {
	        for(int i = 0; i < list.size(); i++)
	        {
	        	for(int j =3;j<list.get(i).size();j++){
	        		 if(list.get(i).get(j).equals(value))
	                     return true;
	        	}
	           
	        }
	        return false;
	    }
		public static ArrayList<ArrayList<String>> trie(ArrayList<ArrayList<String>> l){
		       ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		       int cpt = 0;
		       for (int i =0 ; i <l.size();i++){
		           if (l.get(i).get(2).equals("3")){
		               res.add(l.get(i));
		               cpt++;
		           }
		       }
		       for (int i =0 ; i <l.size();i++){
		           if (l.get(i).get(2).equals("2")){
		               res.add(cpt,l.get(i));
		               cpt++;
		           }
		       }

		       for (int i =0 ; i <l.size();i++){
		           if (l.get(i).get(2).equals("1")){
		               res.add(cpt,l.get(i));
		               cpt++;
		           }
		       }

		       return res;
		   }
}
