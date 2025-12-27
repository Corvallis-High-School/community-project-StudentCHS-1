class Main {
    // 8. Write a main method that constructs at least 2 objects of your class using the constructor and then calls all of the methods that you created above to test them.
  public static void main(String[] args) {
    // 9. Update main method to create at least 3 object of your class using an array of these objects
    // CommunityProject member1 = new CommunityProject("Alice", 1, 50.0);
    // CommunityProject member2 = new CommunityProject("Bob", 2, 75.0);
    CommunityProject[] communityProjects = new CommunityProject[4];
    communityProjects[0] = new CommunityProject("Alice", 1, 50.0);
    communityProjects[1] = new CommunityProject("Bob", 2, 75.0);
    communityProjects[2] = new CommunityProject("Carol", 3, 0.0);
    communityProjects[3] = new CommunityProject("Dave", 2, 25.0);

    // call all of the objects methods to test them
    // Use an indexed for-loop to plain print out the array of communityProjects
    for (int i = 0; i < communityProjects.length; i++){
      communityProjects[i].print();
    }

    // Use an indexed for-loop to formatted print out the array of communityProjects
    for (int i = 0; i < communityProjects.length; i++){
      communityProjects[i].print("table");
    }

    // Use an indexed for-loop to print out the array of communityProjects using toString()
    for (int i = 0; i < communityProjects.length; i++){
      System.out.println(communityProjects[i]);
    }
  }
}