
// 서버
export const REDIRECT_URI = "https://i8c202.p.ssafy.io/kakaoLogin"
export const HOST = `https://i8c202.p.ssafy.io`

// 테스트 환경
// export const REDIRECT_URI = "http://localhost:3000/kakaoLogin"
// export const HOST = `http://localhost:8080`


export const CLIENT_ID = "cb9783828da32ab9af5c189492140e97";
export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
