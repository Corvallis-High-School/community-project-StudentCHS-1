public class CommunityProject {
    // 1. write 3 instance variables for class: private type variableName;
    private String memberName;
    private int assignedBed;
    private double outstandingDues;
    // 2. Add a constructor with 3 parameters to set all of the instance variables to the given parameters.
    public CommunityProject(String memberName, int assignedBed, double outstandingDues){
        this.memberName = memberName;
        this.assignedBed = assignedBed;
        this.outstandingDues = outstandingDues;
    }
    // 3. Write a print() method with arguments that indicate how you want to print out the information, e.g. print(format) could print the data according to an argument that is “plain” or “table” where the data is printed in a table drawn with dashes and lines (|). 
    public void print() {
        // Default to plain format to preserve existing behavior
        print("plain");
    }

    /**
     * Prints the member information according to the requested format.
     * Supported formats (case-insensitive):
     *  - "plain": simple label/value lines.
     *  - "table": a single-row table drawn with dashes (-) and pipes (|).
     *
     * Any other value falls back to "plain".
     *
     * @param format the desired output format, e.g. "plain" or "table"
     */
    public void print(String format) {
        if (format != null && format.equalsIgnoreCase("table")) {
            String border = "+----------------------+-------------+---------------------+";
            System.out.println(border);
            System.out.printf("| %-20s | %-12s | %-18s |%n", "Member Name", "Assigned Bed", "Outstanding Dues");
            System.out.println(border);
            System.out.printf("| %-20s | %-12d | $%-18.2f|%n", memberName, assignedBed, outstandingDues);
            System.out.println(border);
        } else {
            System.out.println("Member Name: " + memberName);
            System.out.println("Assigned Bed: " + assignedBed);
            System.out.println("Outstanding Dues: $" + outstandingDues);
        }
    }
    // 4. Create accessor (get) methods for each of the instance variables.
    public String getMemberName() {
        return memberName;
    }

    public int getAssignedBed() {
        return assignedBed;
    }

    public double getOutstandingDues() {
        return outstandingDues;
    }

    // 5. Create mutator (set) methods for each of the instance variables.
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setAssignedBed(int assignedBed) {
        this.assignedBed = assignedBed;
    }

    public void setOutstandingDues(double outstandingDues) {
        this.outstandingDues = outstandingDues;
    }

    // 6. Create a toString() method that returns all the information in the instance variables.
    @Override
    public String toString() {
        return "Member Name: " + memberName + ", Assigned Bed: " + assignedBed + ", Outstanding Dues: $" + outstandingDues;
    }

    // 7. Write an additional method for your class that takes a parameter. For example, there could be a print method with arguments that indicate how you want to print out the information, e.g. print(format) could print the data according to an argument that is "plain" or "table" where the data is printed in a table drawn with dashes (-) and lines (|).
    public void payDues(double amount) {
        if (amount > 0) {
            outstandingDues -= amount;
            if (outstandingDues < 0) {
                outstandingDues = 0;
            }
        }
    }
}   