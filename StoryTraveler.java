  import java.io.File;
  import java.io.FileReader;
  import java.io.FileWriter;
  import java.io.IOException;
  import java.io.FileNotFoundException;

/**
 * StoryTravler.java. Produces output text from an order K Markov model
 * of provided sample text.
 *
 * @author   Gordon Skinner (GES0004@auburn.edu)
 *
 */
   public class StoryTraveler {
   
      public static void main(String[] args) {
			
			int K = 10;
			int length = 250;
         String inputFile = "dickens.txt";
         String outputFile = "a.txt";
        
         try {
            File source = new File(inputFile);
            FileReader reader = new FileReader(source);
            int i;
            int count = 0;
            while (count < K) {
               i = reader.read();
               if(i == -1) {
                  System.out.println("Error, source contains less than K characters. Terminating...");
                  System.exit(2);
               }
               else {
                  count++;
               }
            }
         	
            LanguageModeler lm= new LanguageModeler(K,source);
            File result = new File(outputFile);
            FileWriter writer = new FileWriter(result);
            StringBuilder sb = new StringBuilder();
            String seed = lm.randomSeed();
            for (int t = 0;t < length;t++){
               if (t == 0){
                  sb.append(seed);
               }
               char c = lm.nextChar(seed);
               sb.append(c);
               if(K > 1){
                  seed = seed.substring(1,K) + c;
               }
               else if(K == 1){
                  seed = Character.toString(c);
               }
            }
            writer.write("");
            writer.write(sb.toString());
            writer.close();
         }
            catch (IOException e) {
               System.out.println("Error, ending run. " + e.toString());
            }
      }
   }