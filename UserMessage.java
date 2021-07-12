package com.example.project3;

public class UserMessage {
    private String recivedId;
    private String SendId;
    private String title;
    private String Content;
    private String Time;
    private int readstate;
    private int writestate;
    public UserMessage(String RecivedId, String SendId,String title, String content, String time,int readstate, int writestate ){
        this.recivedId = RecivedId;
        this.SendId = SendId;
        this.title = title;
        this.Content = content;
        this.Time = time;
        this.readstate=readstate;
        this.writestate = writestate;

    }
    public String GetRecivedId(){return recivedId;}
    public String GetSendId(){return SendId;}
    public String GetTitle(){return title;}
    public String GetTime(){return Time;}
    public int GetReadState(){return readstate;}
    public int GetWriteState(){return writestate;}
    public String getContent(){return Content;}
    public void SetReadState(int num) //0은 미확인 1은 확인 2는 답장
    {
        readstate=num;
    }
    public void SetWriteState(int num) //보낸이확인
    {
        writestate=num;
    }
}
