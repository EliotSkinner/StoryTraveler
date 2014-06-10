   import java.io.File;
   import java.io.FileReader;
   import java.util.Hashtable;
	import java.util.Collection;
   import java.io.IOException;
	import java.util.Random;

/**
 * Creates an order K Markov model for a text sample.
 *
 * @author  Gordon Skinner (GES0004@auburn.edu)
 * 
 *
 */
   public class LanguageModeler {
	
      Hashtable<String,String> hashTable;
		File file;
      FileReader reader;
      String firstSeed;
   
      public LanguageModeler(int K, File text) {
         file = text;
			boolean temp = true;
         String nextCh;
         hashTable = new Hashtable<String,String>();
			
         try {
            reader = new FileReader(file);
            int readnum = reader.read();
            String string = new String("");
            while (readnum != -1) {
               char ch = (char) readnum;
               readnum = reader.read();
               if (string.length() < K){
                  string = string + ch;
               }
               else if (K == 0 && hashTable.get(string) != null){
                  nextCh = hashTable.get(string);
                  nextCh = nextCh + ch;
                  hashTable.put(string,nextCh);
               }
               else if (hashTable.get(string) == null) {
                  if (temp) {
                     firstSeed = string;
                     temp = false;
                  }
                  nextCh = Character.toString(ch);
                  hashTable.put(string,nextCh);
                  if (K != 0) {
                     string = string.substring(1,K) + ch;
                  }
               }
               else {
                  nextCh = hashTable.get(string);
                  nextCh = nextCh + ch;
                  hashTable.put(string,nextCh);
                  string = string.substring(1,K)+ch;
               }
            }
            reader.close();
         }
            catch (IOException e) {
               System.out.println("Error, ending run. " + e.toString());
            }
      
      }
   
   
      public LanguageModeler(int K, String text) {
         hashTable = new Hashtable<String,String>();
         int textLength = text.length();
         String string = "";
			String nextCh = "";
			boolean temp = true;
         String c = "";
         for (int i = 0;i < textLength;i++)
            c = text.substring(i,i+1);
         if(string.length() < K){
            string = string + c;
         }
         else if (hashTable.get(string) == null) {
            if (temp) {
               firstSeed = string;
               temp = false;
            }
            hashTable.put(string,c);
            string = string.substring(1,K) + c;
         }
         else {
            nextCh = hashTable.get(string);
            nextCh = nextCh + c;
            hashTable.put(string,nextCh);
            string = string.substring(1,K) + c;
         }
      }
   
    
      public String firstSeed() {
         return firstSeed;
      }
   
   
      public String randomSeed() {
         Collection<String> list = hashTable.keySet();
         String[] slist = new String[list.size()];
         list.toArray(slist);
			Random rn = new Random();
         int rnum = rn.nextInt(slist.length);
         return slist[rnum];
      }
   
   
      public char nextChar(String seed) {
         char nextCh = '#';
         if(seed.length() == firstSeed.length()){
            String stringCh = hashTable.get(seed);
            int numCh = stringCh.length();
            char[] c = stringCh.toCharArray();
				Random rn = new Random();
            int rnum = rn.nextInt(numCh);
            nextCh = c[rnum];
         }
         return nextCh;
      }

   
   }