import { KAKAO_AUTH_URL } from "./OAuth";
import LoginImg from "../../assets/banner/kakao_large.png"
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";

export default function Login(){

    function KakaoLogin(){
        
        window.location.href=KAKAO_AUTH_URL
 
    }

    
    return(
        <div> 
            <button onClick={KakaoLogin} className="kakaobtn">
            <img
            style={{
                width:300,
            }} 
            src={LoginImg} alt="카카오로그인"></img>
        </button>
        </div>
    )
}