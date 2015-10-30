
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * Created by Georgi on 10/29/2015.
 */
public class DependenciesPackages {
	private ArrayList<PackageInstall> packageInstalls = new ArrayList<>();

	public DependenciesPackages(){
		ReadFromFile();
	}
	
	private void ReadFromFile() {

		Scanner fileInput = null;
		String line;
		String nameFile = "all_packages.json.txt";
		File file = new File(nameFile);
		try {
			fileInput = new Scanner(file);
			while (fileInput.hasNextLine()) {
				line = fileInput.nextLine();
				ReadFromLine(line);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != fileInput) {
				fileInput.close();
			}
		}

	}

	private void ReadFromLine(String temp) {
		PackageInstall p = new PackageInstall();

		String[] arr = temp.split("[ \\[,\\]:\"]+");
		String[] arrTemp = new String[arr.length];
		int k = 0;
		if ((temp.contains("}")) || (temp.contains("{"))) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {

			if (!arr[i].equals("")) {
				p.setName(arr[i]);
				//System.out.println(arr[i]);
				// System.out.println(p.getName());
				for (int j = i + 1; j < arr.length; j++) {
					if (!arr[j].equals("")) {
						arrTemp[k] = arr[j];
						//System.out.println(arrTemp[k]);
						i = j;
						k++;
					}
				}
				p.setNeedPackages(arrTemp);
				packageInstalls.add(p);
			}
		}
		

	}
	public ArrayList<PackageInstall> getPackageInstalls() {
		return packageInstalls;
	}
}
