package com.starcor.aaa.app.service;
interface IAAAService{
    String auth(String authInfo);
    String getInfo(String info);
    String mediaAuth(String authInfo);
    String exchangeMedia(String info);
    String getUserCards(String info);
    String getProductList(String info);
}