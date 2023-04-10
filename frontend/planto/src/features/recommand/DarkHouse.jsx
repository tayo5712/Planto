import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import BottomNav from '../nav/BottomNav'
import MoveTopBtn from "../components/MoveTopBtn"
// 이미지
import TopRecommand from './TopRecommand';
import BackWhite from '../../assets/icons/back_white.png'
import BackBtn from '../components/BackBtn';
import { HOST } from '../login/OAuth'



function Darkhouse() {
  const [dplants, setDPlants] =  useState([]);
  const reconame = 'DarkHouse'
  const navigate = useNavigate();

  useEffect(() => {
    axios({
        method: "get",
        url: `${HOST}/api/v1/dict/dark`,
    }).then(function (response) {
        setDPlants(response.data)
    });
    
}, [])

  return (
    <div>
      {/* <div>
      <button onClick={()=>navigate(-1)}style={{position:'',top:'-1.5%', left:'-4%',opacity:'0.5'}}>
      <img src={BackWhite} alt="back_white" style={{width:"5rem"}} />
      </button>
      </div> */}
      {/* 윗배경 */}
      <TopRecommand reconame = {reconame}/>
      <div className="RecommandPlant">
      {/* 식물리스트 */}
      <div>{dplants.map(dplant=>(<DPlant dplant={dplant} key={dplant.plantDictId}/>))}</div>
      </div>
      {/* 스크롤 여백 */}
      <div style={{height:'4rem'}}></div>
      <MoveTopBtn/>
      <BottomNav/>
    </div>
  )
}

function DPlant({dplant}){
  const arr = dplant.name.split("(")
  const dplantName = arr[0]
  const dplantNameSecond = arr[1] !== undefined ? arr[1].slice(0,-1):""
  


  return (
    <div>
      <Link to={`/dictionary/${dplant.plantDictId}`} state={{plant:dplant, plantName:dplantName, plantNameSecond:dplantNameSecond}}>
        <div className='plant_line'>
          <div className="plant_image"> 
            <div className="img_square" style={{
            backgroundImage: `url("https://www.nongsaro.go.kr/cms_contents/301/${dplant.imagePath}")`
            }}>
            </div>
          </div>
          <div className='plant_text recommand_plant_text'>
            <p className='font-PreL plant_name' style={{marginBottom:'-0.2rem'}}>{dplantName}</p>  
            <p className='font-PreM plant_name_second' style={{marginBottom:'-0.2rem',color:'#3BD476'}}>{dplantNameSecond}</p>
            <p className='font-PreL plant_name_eng'>{dplant.plantEng}</p>
          </div>
        </div>
      </Link>
    </div>
  )



}

export default Darkhouse