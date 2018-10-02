import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.io.*;
import java.util.Collections;
import java.util.ArrayList;

public class find_route{
  public static Integer check_h_value(String h_info[][],String current_city)
  {
    //here i am trying to obtain the h value to be added to the total distance
    int h=0;
    for(int l=0;l<h_info.length;l++)
    {
      if(h_info[l][0].equals(current_city))
      {
        String temp4=h_info[l][1];
        h=Integer.parseInt(temp4);
        break;
      }
    }
    return h;
  }
  public static void a_star_check(String[][] cities_info, String start_city, String end_city,String [][] h_info)
  {
    //here i have arraylist of the visited and fringe to keep track of
    ArrayList<Node>fringe=new ArrayList<Node>();
    ArrayList<Node>visited_nodes=new ArrayList<Node>();
    //have to create the first Node
    int h_numb=check_h_value(h_info,start_city);
    Node start_state=new Node(start_city,h_numb);
    //here creating a temp node to place in visited node once done
    Node current_node=start_state;
    //here im going to add the start state to the fringe to populate the fringe
    fringe.add(start_state);
    //the only way the while loop breaks is if no elements in fringe so means no
    //to get to final distanation
    int bool=0;
    //the loop will break if the fringe is empty
    while(fringe.size()!=0)
    {
      //if the current node is the same as the end city wil go in here and print
      //all the information
      if(current_node.current_city.equals(end_city))
      {
        System.out.println("distance: "+current_node.total_distance+" km");
        System.out.println("route:");
        String route="";
        int store_last_distance=0;
        for(int m=0;m<current_node.parent_cities.size();m++)
        {
          if(m==0)
          {
            continue;
          }
          else if((m+1)==current_node.parent_cities.size())
          {
            route=current_node.parent_cities.get(m)+" to ";
            store_last_distance=current_node.cities_distace.get(m);
          }
          else
          {
            System.out.println(current_node.parent_cities.get(m)+" to "+ current_node.parent_cities.get(m+1)+", "+current_node.cities_distace.get(m)+"km");
          }
        }
        System.out.println(route+current_node.current_city+" , "+store_last_distance+"km");
        bool=1;
        break;
      }
      //here goes in here and runs all the info of the cities
      for(int i=0;i<cities_info.length;i++)
      {
        //here if city is found, then will try to expand it
        if(current_node.current_city.equals(cities_info[i][0]))
        {
          //here obtaining the heuristic to be added to the total distance
          int hh_value=check_h_value(h_info,cities_info[i][1]);
          //creates the node
          ArrayList<String>temp2=new ArrayList<String>(current_node.parent_cities);
          ArrayList<Integer>temp3=new ArrayList<Integer>(current_node.cities_distace);
          Node state=new Node(cities_info[i][1],cities_info[i][2],current_node.current_city,temp2,temp3,current_node.total_distance,hh_value);
          int check_1=0;
          //if not visited will automatically add to visited and fringe
          if(visited_nodes.size()==0)
          {
            check_1=0;
          }
          else
          {
            //here make sure that the node has not been visted, if it has then doesnt
            //add to the fringe or visted arraylist
            for(int j=0;j<visited_nodes.size();j++)
            {
              //if node is already in the visted node then dont need to add to fringe
              if(state.current_city.equals(visited_nodes.get(j).current_city))
              {
                check_1=1;
              }

            }
          }
          //if everythin checks will be added to the fringe
          if(check_1==0)
          {
              fringe.add(state);
          }
        }
        //now comparing eith the other side of the array of info
        else if(current_node.current_city.equals(cities_info[i][1]))
        {
          //creating the node
          //getting the heauristic to be added to take into account
          int hh_value=check_h_value(h_info,cities_info[i][0]);
          ArrayList<String>temp2=new ArrayList<String>(current_node.parent_cities);
          ArrayList<Integer>temp3=new ArrayList<Integer>(current_node.cities_distace);
          Node state=new Node(cities_info[i][0],cities_info[i][2],current_node.current_city,temp2,temp3,current_node.total_distance,hh_value);
          int check_1=0;
          //now if the visited node arratylist is empty will add to fringe
          if(visited_nodes.size()==0)
          {
            check_1=0;
          }
          else
          {
            //if there are visted nodes then make sure the current node is not in
            //the visted so that you could add to the fringe
            for(int j=0;j<visited_nodes.size();j++)
            {
              //if it is already in visted then dont add again
              if(state.current_city.equals(visited_nodes.get(j).current_city))
              {
                check_1=1;
              }
            }
          }
          //if everythng is good add to fringe
          if(check_1==0)
          {
              fringe.add(state);
          }
        }
        else
        {
        }
      }
      //here i will place the temp/paremnt in visited since done using it
      visited_nodes.add(current_node);
      //now remove the node from fringe
      fringe.remove(0);
      //if it 0 then dont sort
      if(fringe.size()==0)
      {
        continue;
      }
      //here we are sorting the objects in the fringe based on total distance with the
      //heuristics
      Collections.sort(fringe,new sort_h_objects());
      current_node=fringe.get(0);
    }
    //if no solution will porint this
    if(bool==0)
    {
      System.out.println("distance: infinity");
      System.out.println("route:");
      System.out.println("none");
    }
    bool=0;
  }
  public static void uniform_check(String[][] cities_info, String start_city, String end_city)
  {
    //here i have arraylist of the visited and fringe to keep track of
    //will put nodes inside both
    ArrayList<Node>fringe=new ArrayList<Node>();
    ArrayList<Node>visited_nodes=new ArrayList<Node>();
    //have to create the first Node
    Node start_state=new Node(start_city);
    //here creating a temp node to place in visited node once done
    Node current_node=start_state;
    //here im going to add the start state to the fringe to populate the fringe
    fringe.add(start_state);
    //the only way the while loop breaks is if no elements in fringe so means no
    //to get to final distanation
    int bool=0;
    //the loop will break if the fringe is empty
    while(fringe.size()!=0)
    {
      //if the current node is the same as the end city wil go in here and print
      //all the information
      if(current_node.current_city.equals(end_city))
      {
        System.out.println("distance: "+current_node.total_distance+" km");
        System.out.println("route:");
        String route="";
        int store_last_distance=0;
        for(int m=0;m<current_node.parent_cities.size();m++)
        {
          if(m==0)
          {
            continue;
          }
          else if((m+1)==current_node.parent_cities.size())
          {
            route=current_node.parent_cities.get(m)+" to ";
            store_last_distance=current_node.cities_distace.get(m);
          }
          else
          {
            System.out.println(current_node.parent_cities.get(m)+" to "+ current_node.parent_cities.get(m+1)+", "+current_node.cities_distace.get(m)+"km");
          }
        }
        System.out.println(route+current_node.current_city+" , "+store_last_distance+"km");
        bool=1;
        break;
      }
      //here goes in here and runs all the info of the cities, here is where we
      //will get all the expansions of the current node to add to fringe
      for(int i=0;i<cities_info.length;i++)
      {
        //here if city is found, then will try to expand it and create a node
        //on the city that is connceted to it
        if(current_node.current_city.equals(cities_info[i][0]))
        {
          //creates the node
          ArrayList<String>temp2=new ArrayList<String>(current_node.parent_cities);
          ArrayList<Integer>temp3=new ArrayList<Integer>(current_node.cities_distace);
          Node state=new Node(cities_info[i][1],cities_info[i][2],current_node.current_city,temp2,temp3,current_node.total_distance);
          int check_1=0;
          //if not visited will automatically add to visited and fringe
          if(visited_nodes.size()==0)
          {
            check_1=0;
          }
          else
          {
            for(int j=0;j<visited_nodes.size();j++)
            {
              //if node is already in the visted node then dont need to add to fringe
              if(state.current_city.equals(visited_nodes.get(j).current_city))
              {
                check_1=1;
              }
            }
          }
          //if the check_1==0 then add to fringe
          if(check_1==0)
          {
              fringe.add(state);
          }
        }
        //now check the other side of the and see if the current is equal
        //if finds it will create another node of the side that is connected
        else if(current_node.current_city.equals(cities_info[i][1]))
        {
          //creating the node
          ArrayList<String>temp2=new ArrayList<String>(current_node.parent_cities);
          ArrayList<Integer>temp3=new ArrayList<Integer>(current_node.cities_distace);
          Node state=new Node(cities_info[i][0],cities_info[i][2],current_node.current_city,temp2,temp3,current_node.total_distance);
          int check_1=0;
          //if the visted ArrayList is 0 add the node to the fringe
          if(visited_nodes.size()==0)
          {
            check_1=0;
          }
          //if not epmty will search to see it is not there. if it it there not add
          //to the fringe
          else
          {
            for(int j=0;j<visited_nodes.size();j++)
            {
              //if it is already in visted then dont add again
              if(state.current_city.equals(visited_nodes.get(j).current_city))
              {
                check_1=1;
              }
            }
          }
          if(check_1==0)
          {
              fringe.add(state);
          }
        }
        else
        {
        }
      }
      //here i will place the temp/paremnt in visited since done using it
      visited_nodes.add(current_node);
      //now remove the node from fringe
      fringe.remove(0);
      if(fringe.size()==0)
      {
        continue;
      }
      //here sorting the nodes from least to greatest depending on total distance
      Collections.sort(fringe,new sort_objects());
      //make the current-node equal to next node to be expanded
      current_node=fringe.get(0);
    }
    //if no solution found print this
    if(bool==0)
    {
      System.out.println("distance: infinity");
      System.out.println("route:");
      System.out.println("none");
    }
    bool=0;
  }
  public static String open_file(String[] args)
  {
    //here will attempt to open up the 1st file
    File temp = new File(args[1]);
    Scanner input_file;
  try
  {
    input_file = new Scanner(temp);
  }
  catch (Exception e)
  {
    System.out.printf("Failed to open file\n");
    return null;
  }
  //will put all the context of file in an arraylist
  ArrayList<String>retrieve_file= new ArrayList<String>();
  while(input_file.hasNextLine())
  {
    String line = input_file.nextLine();
    retrieve_file.add(line);
  }
  //close the file once i am done scanning the whole thing
  input_file.close();
  //now restore the file in a 2d array to use later
  String [][]cities_info=new String [retrieve_file.size()][];
  for(int i=0;i<retrieve_file.size();i++)
  {
      String lines=retrieve_file.get(i);
      cities_info[i]=lines.split(" ");
  }
  //here will obtain the start city and the end city
  String start_city=args[2];
  String end_city=args[3];
  //here if there is 6 arguments then it has to read another File
  //it is the same process as reading the 1st file
  if(args[0].equals("inf"))
  {
    File tempp = new File(args[4]);
    Scanner input_file_2;
    try
    {
      input_file_2 = new Scanner(tempp);
    }
    catch (Exception e)
    {
      System.out.printf("Failed to open file\n");
      return null;
    }
    ArrayList<String>retrieve_file_2= new ArrayList<String>();
    while(input_file_2.hasNextLine())
    {
      String line = input_file_2.nextLine();
      retrieve_file_2.add(line);
    }
    input_file_2.close();
    String [][]h_info=new String [retrieve_file_2.size()][];
    for(int i=0;i<retrieve_file_2.size();i++)
    {
        String lines=retrieve_file_2.get(i);
        h_info[i]=lines.split(" ");
    }
    String type=args[0];
    //if it able to read the 2 files will go in the a* function
    a_star_check(cities_info,start_city,end_city,h_info);
  }
  else if(args[0].equals("uninf"))
  {
    //will pass them down to a new function to begin
    String type=args[0];
    String [][]h_info=new String [2][];
    //goes in here if everything is up to track with opening the file
    uniform_check(cities_info,start_city,end_city);
  }
  else
  {

  }
  return "complete";
  }
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    //here did if statements to get how many args are passed in
    boolean arg_size=true;
    //here will go check how many arguments we have and based in that will go to right function
    if(args.length<=3)
    {
      System.out.println("Need more arguments");
      arg_size=false;
    }
    else if(args.length==4)
    {
      //goes in here if it is for uniform_check
      arg_size=true;
    }
    else if(args.length==5)
    {
      //goes in here if it is a*
      arg_size=true;
    }
    else
    {
      arg_size=false;
    }
    //if there are 5 or 6 arguments will go here else wont go to next function
    if(arg_size==true)
    {
      //will read al the files
      String check=open_file(args);
    }
    else
    {
      System.out.println("arguments are invalid");
    }
  }
}
class Node {
  //here are the variables i need for the node
  public String current_city;
  ArrayList<String> parent_cities=new ArrayList<String>();
  ArrayList<Integer> cities_distace=new ArrayList<Integer>();
  public int parent_distance;
  public String parent_name;
  public int total_distance=0;
  public int heuristic=0;
  public int new_h_total=0;
  //this contractor we are just making the 1st node making everything simple
  public Node(String c_city)
  {
    current_city=c_city;
    total_distance=0;
    parent_name="none";
    parent_distance=0;
    parent_cities.add(parent_name);
    cities_distace.add(parent_distance);
  }
  //constructor
  public Node(String c_city, String c_distance,String p,ArrayList<String>pc,ArrayList<Integer>current_d,Integer parent_total)
  {
    //making the current city equal to c_city
    current_city=c_city;
    //converting the distance to int
    int distance=Integer.parseInt(c_distance);
    parent_distance=distance;
    int t=parent_distance+parent_total;
    parent_name=p;
    total_distance=total_distance+t;
    parent_cities=pc;
    cities_distace=current_d;
    parent_cities.add(parent_name);
    cities_distace.add(parent_distance);
  }
  //this class is used to create the very firat node havin]g the h_value
  public Node(String c_city,Integer h_value)
  {
    current_city=c_city;
    heuristic=h_value;
    total_distance=0;
    parent_name="none";
    parent_distance=0;
    new_h_total=heuristic;
    parent_cities.add(parent_name);
    cities_distace.add(parent_distance);
  }
  //here creating the node containing h-value
  public Node(String c_city, String c_distance,String p,ArrayList<String>pc,ArrayList<Integer>current_d,Integer parent_total,Integer h_value)
  {
    current_city=c_city;
    int distance=Integer.parseInt(c_distance);
    parent_distance=distance;
    int t=parent_distance+parent_total;
    parent_name=p;
    total_distance=total_distance+t;
    parent_cities=pc;
    cities_distace=current_d;
    heuristic=h_value;
    new_h_total=heuristic+total_distance;
    parent_cities.add(parent_name);
    cities_distace.add(parent_distance);
  }
}
//here i am sorting to objects based on totoal distance
class sort_objects implements Comparator<Node>{
  public int compare(Node a, Node b)
    {
        return a.total_distance - b.total_distance;
    }
}
//here sorting based on toal distance plus h_value
class sort_h_objects implements Comparator<Node>{
  public int compare(Node a, Node b)
    {
        return a.new_h_total - b.new_h_total;
    }
}
