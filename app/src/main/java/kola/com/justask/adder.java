package kola.com.justask;

/**
 * Created by acer on 3/22/2018.
 */

public class adder {
    String id;
    String name;
    String ques;String image;String ans;
     public adder(){}
    public adder(String id,String name, String ques,String image,String ans) {
        this.id=id;
        this.name = name;
        this.ques = ques;
        this.image=image;
        this.ans=ans;
      //  this.ans=ans;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQues() {
        return ques;
    }

    public String getImage() {
        return image;
    }
    public String getans() {
        return ans;
    }


}
