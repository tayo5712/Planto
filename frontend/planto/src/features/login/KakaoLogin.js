import React, { useEffect } from 'react'
// import { useNavigate } from 'react-router-dom';
import {HOST} from "./OAuth";
import axios from 'axios'
import { useNavigate } from 'react-router-dom';



function KakaoLogin() {
    
    const navigate = useNavigate();
    // TOKEN저장
    
    useEffect(() => {
        (async () => {
        const PARAMS = new URL(document.location).searchParams;
        const KAKAO_CODE = PARAMS.get('code');
    
        console.log({KAKAO_CODE})
        const backHost = `${HOST}/api/v1/oauth/token?code=${KAKAO_CODE}`
        await axios({

            url: backHost,
            method: 'GET',
        })
        .then((res) =>{
            console.log(res)
            const token = res.headers.authorization
            window.localStorage.setItem('token', token);
            // 로컬스토리지에 토큰이 있으면 메인페이지로 이동
            // 없으면 로그인 페이지로 리다이렉트
            if (token) {
                navigate('/main')
            } else {
                navigate('/')
            }
        })
        .catch((err) => {
            console.log(err)
        })
        
 
    })();
    }, [])


    return (
    <div>

        {/* 수정 */}

    </div>
  )
}

export default KakaoLogin