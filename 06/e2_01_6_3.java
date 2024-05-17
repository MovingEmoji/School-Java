//Naoya iida
class e2_01_6_3 {
    public static void main(String[] args) {
        ReportGenerator r1 = new PDFReportGenerator();
        r1.generateReport();
        System.out.println("-------------------------");
        ReportGenerator r2 = new HTMLReportGenerator();
        r2.generateReport();
    }
}
abstract class ReportGenerator {
    public final void generateReport() {
        generateHeader();
        generateBody();
        generateFooter();
    }

    protected abstract void generateHeader();

    protected abstract void generateBody();

    protected abstract void generateFooter();
}

class PDFReportGenerator extends ReportGenerator {
    @Override
    protected void generateHeader() {
        System.out.println("Generating PDF Report Header");
    }
    @Override
    protected void generateBody() {
        System.out.println("Generating PDF Report Body");
    }
    @Override
    protected void generateFooter() {
        System.out.println("Generating PDF Report Footer");
    }
}
class HTMLReportGenerator extends ReportGenerator {
    @Override
    protected void generateHeader() {
        System.out.println("Generating HTML Report Header");
    }
    @Override
    protected void generateBody() {
        System.out.println("Generating HTML Report Body");
    }
    @Override
    protected void generateFooter() {
        System.out.println("Generating HTML Report Footer");
    }
}
    
    
    
/*
Generating PDF Report Header
Generating PDF Report Body
Generating PDF Report Footer
-------------------------
Generating HTML Report Header
Generating HTML Report Body
Generating HTML Report Footer
 */