package com.jwhh;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Main {

    public static void main(String[] args) {
	    //doTryCatchFinally();
//       doTryWithResources();
//     doTryWithResourcesMulti();
    doCloseThing();
    }

    public static void doTryCatchFinally() {
        char[] buff = new char[8];
        int length;
        Reader reader = null;
        try  {
            reader = Helper.openReader("file1.txt");
            while((length = reader.read(buff)) >= 0)
            {
                System.out.println("\nlength: " + length);
                for(int i=0; i < length; i++)
                    System.out.print(buff[i]);
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        finally {
            try {
                if (reader != null)
                    reader.close();
            }
            catch(IOException e2) {
                System.out.println(e2.getClass().getSimpleName() + " - " + e2.getMessage());
            }
        }
    }

    public static void doTryWithResources() {
        char[] buff = new char[8];
        int length;
        try (Reader reader = Helper.openReader("file1.txt")) {
            while((length = reader.read(buff)) >= 0) {
                System.out.println("\nlength: " + length);
                for(int i=0; i < length; i++)
                    System.out.print(buff[i]);
            }
        } catch(IOException e) {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
    //In "try with resources" you have to create object of Reader inside of parenthesis of try block and after
    // that there is no need to define the finally block
    //Like doTryCatchFinally()
    //----------------------------------------------------------------------------------------------------------------------------

    public static void doTryWithResourcesMulti()
    {
        char[] buff = new char[10];
        int length;
        try (Reader reader = Helper.openReader("file1.txt");  Writer writer = Helper.openWriter("file2.txt"))
        {
            while((length = reader.read(buff)) >= 0)
            {
                System.out.println("\nlength: " + length);
                writer.write(buff, 0, length);
            }
        } catch(IOException e)
        {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
    //in doTryWithResourceMulti() we are reading from txt1.txt file by reader object and writing it in file2.txt
    //by writer object and in the end both file are closing without getting mentioned that's the beauty of try with resource
    //------------------------------------------------------------------------------------------------------------------------------

    private static void doCloseThing() {
        try(  MyAutoCloseable ac = new MyAutoCloseable()  )
        {
            ac.saySomething();
        }
        catch (IOException e)
        {
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());

            for(Throwable t:e.getSuppressed()) //without getSuppressed(), catch{} will only print one exception which is in saySomething() method
                System.out.println("Suppressed: " + t.getMessage());
        }
    }

}
