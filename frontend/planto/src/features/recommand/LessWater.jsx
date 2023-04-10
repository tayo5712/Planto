import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom'
// import component
import BottomNav from '../nav/BottomNav'
import MoveTopBtn from "../components/MoveTopBtn"

import "./LessWater.css"
// 이미지
import TopRecommand from './TopRecommand';
import BackWhite from '../../assets/icons/back_white.png'
import { HOST } from '../login/OAuth'

function LessWater() {
  const [wplants, setWplants] =  useState([]);
  const navigate = useNavigate();
  const reconame = 'LessWater'
  useEffect(() => {
    axios({
        method: "get",
        url: `${HOST}/api/v1/dict/intermediate`,
    }).then(function (response) {
        setWplants(response.data)
    });
    
}, [])

  return (
    <div>
      {/* <div>
      <button onClick={()=>navigate(-1)}style={{position:'fixed',top:'-1.5%', left:'-4%',opacity:'0.5'}}>
      <img src={BackWhite} alt="back_white" style={{width:"5rem"}} />
      </button>
      </div> */}
      <TopRecommand reconame = {reconame}/>
      <div className="RecommandPlant">
      <div>{wplants.map(wplant=>(<Wplant wplant={wplant} key={wplant.plantDictId}/>))}</div>
      </div>
      <div style={{height:'4rem'}}></div>
      <MoveTopBtn/>
      <BottomNav/>
    </div>
  )
}

function Wplant({wplant}){
  const arr = wplant.name.split("(")
  const wplantName = arr[0]
  const wplantNameSecond = arr[1] !== undefined ? arr[1].slice(0,-1):""
  


  return (
    <div>
      <Link to={`/dictionary/${wplant.plantDictId}`} state={{plant:wplant, plantName:wplantName, plantNameSecond:wplantNameSecond}}>
        <div className='plant_line' style={{marginBottom:'1.2rem',}}>
          <div className="plant_image"> 
            <div className="img_square" style={{
            backgroundImage: `url("https://www.nongsaro.go.kr/cms_contents/301/${wplant.imagePath}")`
            }}>
            </div>
          </div>
          <div className='plant_text'>
            <p className='font-PreL plant_name' style={{marginBottom:'-0.2rem'}}>{wplantName}</p>  
            <p className='font-PreM plant_name_second' style={{marginBottom:'-0.2rem',color:'#3BD476'}}>{wplantNameSecond}</p>
            <p className='font-PreL plant_name_eng'>{wplant.plantEng}</p>
          </div>
        </div>
      </Link>
    </div>
  )



}

export default LessWater