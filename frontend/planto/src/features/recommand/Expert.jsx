import axios from 'axios';
import React, {useState, useEffect} from 'react'
import {Link,useNavigate} from 'react-router-dom'

import BottomNav from '../nav/BottomNav'
import MoveTopBtn from "../components/MoveTopBtn"
import TopRecommand from './TopRecommand';
import BackWhite from '../../assets/icons/back_white.png'
import { HOST } from '../login/OAuth'


function Expert() {
  const [iplants, setIPlants] =  useState([]);
  const navigate = useNavigate();
  const reconame = 'Expert'

  useEffect(() => {
    axios({
        method: "get",
        url: `${HOST}/api/v1/dict/intermediate`,
    }).then(function (response) {
        setIPlants(response.data)
    });
    
}, [])
  return (
    <div>
      <div>
      <button onClick={()=>navigate(-1)}style={{position:'fixed',top:'-1.5%', left:'-4%',opacity:'0.5'}}>
      <img src={BackWhite} alt="back_white" style={{width:"5rem"}} />
      </button>
      </div>
      <div>
      <TopRecommand reconame={reconame}/>
      <div className="RecommandPlant">
      <div>{iplants.map(iplant=>(<IPlant iplant={iplant} key={iplant.plantDictId}/>))}</div>
      </div>

      <div style={{height:'4rem'}}></div>
      <MoveTopBtn/>
      <BottomNav/>
    </div>
    </div>
  )
}

function IPlant({iplant}){
  const arr = iplant.name.split("(")
  const iplantName = arr[0]
  const iplantNameSecond = arr[1] !== undefined ? arr[1].slice(0,-1):""
  


  return (
    <div>
      <Link to={`/dictionary/${iplant.plantDictId}`} state={{plant:iplant, plantName:iplantName, plantNameSecond:iplantNameSecond}}>
        <div className='plant_line' >
          <div className="plant_image"> 
            <div className="img_square" style={{
            backgroundImage: `url("https://www.nongsaro.go.kr/cms_contents/301/${iplant.imagePath}")`
            }}>
            </div>
          </div>
          <div className='plant_text'>
            <p className='font-PreL plant_name' style={{marginBottom:'-0.2rem'}}>{iplantName}</p>  
            <p className='font-PreM plant_name_second' style={{marginBottom:'-0.2rem',color:'#3BD476'}}>{iplantNameSecond}</p>
            <p className='font-PreL plant_name_eng'>{iplant.plantEng}</p>
          </div>
        </div>
      </Link>
    </div>
  )



}
export default Expert