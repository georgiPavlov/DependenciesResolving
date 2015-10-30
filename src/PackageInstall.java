

	public class PackageInstall {
	    private String name;
	    private String[] needPackages;
	    
	    public PackageInstall(){
	    	this.name=null;
	    	this.needPackages=null;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String[] getNeedPackages() {
	        return needPackages;
	    }

	    public void setNeedPackages(String[] needPackages) {
	        this.needPackages = needPackages;
	    }
	}



