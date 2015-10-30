import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class MyDependencies {
    private String startInstall;
    private ArrayList<PackageInstall> p = new ArrayList<>();

    public MyDependencies(){
        this.allPackagesSetup();
        this.readFromFile();
        this.installer(this.startInstall);
        System.out.println("All done");
    }

    private void readFromFile() {

        Scanner fileInput = null;
        StringBuilder builder = new StringBuilder();
        String line;
        String nameFile = "dependencies.json.txt";
        File file = new File(nameFile);

        try {
            fileInput = new Scanner(file);
            while (fileInput.hasNextLine()) {
                line = fileInput.nextLine();
                builder.append(line);

            }
            String finalResult = builder.toString();
            readFromLine(finalResult);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != fileInput) {
                fileInput.close();
            }
        }

    }

    private void readFromLine(String temp) {

        String[] arr = temp.split("[ }\\[\\]\"]+");
        this.startInstall = arr[arr.length - 1];
        //System.out.println(startInstall);
    }

    private void allPackagesSetup() {
        DependenciesPackages d = new DependenciesPackages();
        p = d.getPackageInstalls();
    }

    private void installer(String name) {
        String[] needToInstall;
        String[] needNotToInstall;
        String cat = null;
        Writer writer = null;
        int v = 0;
        int z = 0;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).getName().equals(name)) {
                String fileString = "installed_modules\\" + name + ".json.txt";
                File f = new File(fileString);
                if (f.exists()) {
                    System.out.println(name + " is already installed");
                    return;
                } else {
                    System.out.println("Installing " + name);
                    if (p.get(i).getNeedPackages() == null) {
                        //System.out.println("Installing " + name);
                        Writer writer2 = null;

                        try {
                            writer2 = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream("installed_modules\\" + name + ".json.txt"), "utf-8"));
                            writer2.write("Something");
                        } catch (IOException ex) {
                            // report
                        } finally {
                            try {
                                writer2.close();
                            } catch (Exception ex) {/*ignore*/}
                        }
                        return;
                    } else {
                        File f2;
                        String[] arr1 = p.get(i).getNeedPackages();
                        needToInstall = new String[arr1.length];
                        needNotToInstall = new String[arr1.length];
                        for (int j = 0; j < arr1.length; j++) {
                            String fileString2 = "installed_modules\\" + arr1[j] + ".json.txt";
                            f2 = new File(fileString2);
                            if (cat == null) {
                                if (arr1[j] != null) {
                                    cat = "In order to install " + name + ", we need " + arr1[j];
                                }
                            } else {
                                if (arr1[j] != null) {
                                    cat = cat + " and " + arr1[j];
                                }
                            }

                            if (!f2.exists()) {
                                needToInstall[v] = arr1[j];
                                v++;
                            } else {
                                needNotToInstall[z] = arr1[j];
                                z++;
                            }
                        }
                        if (cat != null) {
                            System.out.println(cat);
                        }
                        ifFileDoesNotExist(needToInstall, name);
                        ifFileDoesExist(needNotToInstall);
                    }
                }
            }

        }

    }

    private void ifFileDoesNotExist(String[] needToInstall, String name) {


        for (int k = 0; k < needToInstall.length; k++) {
            installer(needToInstall[k]);
            Writer writer1 = null;
            try {
                writer1 = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("installed_modules\\" + name + ".json.txt"), "utf-8"));
                writer1.write("Something");
            } catch (IOException ex) {
                // report
            } finally {
                try {
                    writer1.close();
                } catch (Exception ex) {/*ignore*/}
            }
        }

    }

    private void ifFileDoesExist(String[] needNotToInstall) {
        for (int i = 0; i < needNotToInstall.length; i++) {
            if (needNotToInstall[i] != null) {
                System.out.println(needNotToInstall[i] + " is already installed");
            }
        }
    }



}
