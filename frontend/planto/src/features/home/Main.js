import './main.css';
import BottomNav from '../nav/BottomNav';
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from 'axios';
import {HOST} from "../login/OAuth";
import MainNone from '../home/MainNone'
import MainPlanto from './MainPlanto';
// import { atom } from 'recoil';
// import { recoilPersist } from 'recoil-persist';



function Main(){
    const navigate = useNavigate();

    const [userdata, setUserdata] = useState();

    //  username 받아오기 구현
    
    const token = window.localStorage.getItem('token');
    useEffect(() => {
        
        try {
          axios.get(`${HOST}/api/v1/user/oauth/username`, {
            headers: {
              Authorization: token,
            
            },
          }
          ).then((res) => {
            setUserdata(res.data)
            window.localStorage.setItem("username",res.data.name)
            window.localStorage.setItem("profileImageUrl",res.data.profileImageUrl)
          });
          
        } 
        catch (e) {
          console.error(e);
        }
        }, [])

        // 플랜토 유무
        const [planto, setPlanto] = useState([]);
        useEffect(() => {
          axios({
              method: "get",
              url: `${HOST}/api/v1/pot`,
              headers: {
                Authorization: token,
              },
          }).then(function (response) {
              setPlanto(response.data)
              console.log(response.data)
          }).catch((e) =>{
              console.log(e)
          });
          
        }, [])
        
    // const plantoList = planto.map((v)=>(<MainPlantoList potId={v[0]} plant={v[1]} user={v[2]} />))
    // const MainPlantoList = (props) =>{
    //   return (
    //     <div>
    //     </div>
    //   )
    // }

    return(
        <div style={{ backgroundColor: '#FAF8F8',height:'100vh',paddingBottom:'10rem' }}>
            <div className="Myplanto_title">
                <span className="font-PreR"> My</span>
                <span className="font-PreEB"> Planto </span>
            </div>
            <div className="Myplanto_name">
                <span className="font-PreR"> 반가워요,</span> 
                <span className="font-PreEB">{userdata ? userdata.name : null}</span><span className="font-PreR">님!</span>
            </div>

            <div>
              {planto.length !== 0 ?
              <MainPlanto plantos={planto}/>:
              <MainNone/>}
            </div>
            
            
            
            <BottomNav/>

        </div>
    );

}

export default Main;