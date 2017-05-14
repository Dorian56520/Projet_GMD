package Search;

import java.util.ArrayList;

import java.util.Date;


public class SearchHpoOrphaOmim {

	

	public static void SearchHpoOrphaOmim (String[] items)
	{
		Date start = new Date();
		int cpt=0;
		//1st 
    	ArrayList<String> Drugdata = Sider.GetSiderDrugData(items);
    	
    	ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(items);
    	ArrayList<String[]> HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
    	ArrayList<ArrayList<String>> CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);
    	
    	ArrayList<String> Diseasedata = SearchOmimtxt.SearchOmimtxtCS(items);
    	ArrayList<ArrayList<String>> CUIandDiseaseOmim = SearchOmimtsv.SearchOmimtsvCUIandDisease(Diseasedata);
		
		/*ArrayList<String[]> CUIandDiseaseHPO = SearchHpoFinale.SearchHpof(items);
		//Then concat all CUI
		ArrayList<String[]> AllCUI = new ArrayList<String[]>();
		
		for(String[] value : CUIandDiseaseOrpha)
		{
			AllCUI.add(value);
		}
		for(String[] value : CUIandDiseaseOmim)
		{
			if(!Contains(value,AllCUI))
				AllCUI.add(value);
			else
				cpt++;
		}
		for(String[] value : CUIandDiseaseHPO)
		{
			if(!Contains(value,AllCUI))
				AllCUI.add(value);
			else
				cpt++;
		}
		System.out.println("Nb doublon : " + cpt);
    	//SE Drug
		ArrayList<String> DrugATC = SearchStitch.SearchStitchAll(Drugdata);
		ArrayList<String> SELabels = SearchATC.SearchATC(DrugATC);

    	//Thus search cure drug
		ArrayList<String> Stitch = Sider.GetStitchIDfromCUI(AllCUI);
		ArrayList<String> ATC = SearchStitch.SearchStitchAll(Stitch);
		ArrayList<String> Labels = SearchATC.SearchATC(ATC);*/
		Date end = new Date();
		System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	}
	public static boolean Contains(String[] value, ArrayList<String[]> list)
	{
		for(String[] data : list)
		{
			if(data[0].equals(value[0]))
				return true;
		}
		return false;
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
	
	
	 public static void main(String[] args) {
			Date start = new Date();
		 String[] items = new String[] {"Abnormal"};
		 ArrayList<String> Diseasedata = SearchOmimtxt.SearchOmimtxtCS(items);
	        ArrayList<ArrayList<String>> CUIandDiseaseOmim = SearchOmimtsv.SearchOmimtsvCUIandDisease(Diseasedata);
	    	
	    
	        ArrayList<String[]> OrphaID = searchOrphadata.getOrphadataData(items);
	        ArrayList<String[]> HPidsAndDisease = HpoSqliteLucas.GetHPidFROMOrphaID(OrphaID);
	        ArrayList<ArrayList<String>> CUIandDiseaseOrpha = SearchHpo.GetCUIFromHPOid(HPidsAndDisease);	
	        ArrayList<ArrayList<String[]>> IDandCUIList = SearchHpo.SearchHPOCUIandHPOids(items);
			ArrayList<String[]> IDandLabel = HpoSqliteLucas.GetCUI(IDandCUIList);
			
			ArrayList<ArrayList<String>> DiseaseANDcui = new ArrayList<ArrayList<String>>();
			MagouillepourHPO(IDandLabel,IDandCUIList,DiseaseANDcui);
	    	
	    	ArrayList<ArrayList<String>> presquefin = combin(CUIandDiseaseOrpha,DiseaseANDcui, CUIandDiseaseOmim);
	    	presquefin = trie(presquefin);
	    	ArrayList<ArrayList<String>> AllStitchID = Sider.GetStitchIDfromCUI(presquefin);
	    	ArrayList<ArrayList<String>> ATC = SearchStitch.SearchStitchAll(AllStitchID);
			ArrayList<ArrayList<String>> Labels = SearchATC.SearchATC(ATC);
			Date end = new Date();
		    System.out.println(end.getTime() - start.getTime() + " Total milliseconds");
	    	//SearchHpoFinale.affiche2(presquefin);
	    	
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
	 public static int Contains(String value, ArrayList<ArrayList<String>> list)
		{
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).get(0).equals(value))
					return i;
			}
			return -1;
		}
	 public static void MagouillepourHPO(ArrayList<String[]> IDandLabel, ArrayList<ArrayList<String[]>> IDandCUIList, ArrayList<ArrayList<String>> DiseaseANDcui)
	 {
		 for(String[] arg : IDandLabel)
			{
				ArrayList<String> tmp = new ArrayList<String>();
				int ind = Contains(arg[1],DiseaseANDcui);
				if(ind < 0)
				{
					tmp.add(arg[1]);
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
						if(value.equals(""))
							break;
						if(value.matches(".*[,;].*"))
						{
							String[] cuis = value.split("[,;]");
							for(String cui : cuis)
								tmp.add(cui.trim());
						}
						else
							tmp.add(value.trim());
						DiseaseANDcui.add(tmp);
						}
				}
				else
				{
					String value = "";
					for(ArrayList<String[]> IDandCUI : IDandCUIList)
					{
						for(String[] val : IDandCUI)
						{
							if(arg[0].equals(val[0]))
							{
								value = val[1];
								break;
							}
						}
					}
					if(value.matches(".*[,;].*"))
					{
						String[] cuis = value.trim().split("[,;]");
						for(String cui : cuis)
							DiseaseANDcui.get(ind).add(cui.trim());
					}
					else
						DiseaseANDcui.get(ind).add(value.trim());
				}
			}
	 }
}
