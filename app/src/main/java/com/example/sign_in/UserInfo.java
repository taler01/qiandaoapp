package com.example.sign_in;

public class UserInfo {

        private Payload  payload;
        private String state;
        public String getState(){
            return state;
        }
        public Payload getPayload(){
            return payload;
        }

}
class Payload{
    private String token;
    public String getToken() {
        return token;

    }
}