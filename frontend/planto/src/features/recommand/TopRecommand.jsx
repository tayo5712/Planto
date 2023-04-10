import React from 'react'
import './TopRecommand.css'

import easyBig from '../../assets/icons/easybig.png'
import hardBig from '../../assets/icons/hardbig.png'
import dark from '../../assets/icons/dark.png'
import watercan from '../../assets/icons/watercan.png'
import BackWhite from '../../assets/icons/back_white.png'
import { Navigate, useNavigate } from 'react-router-dom'
import BackBtn from '../components/BackBtn'

// import BackBtn from '../components/BackBtn';



function TopRecommand({reconame}) {
    const now = reconame
    const navigate = useNavigate();
  return (

    
    <div>
    {
    {
        Beginner :  
        <div className="toprecommand">
            {/* 뒤로가기 */}
            <div className="back_button">
            <button onClick={()=>navigate(-1)}style={{position: 'fixed'}}>
            <img src={BackWhite} alt="back_white" style={{width:'4rem',}}/>
            </button>
            </div>
            <div className='beginnerBack'>
                <img src={easyBig} alt="Easy Plant" style={{width:'10rem',position:'absolute',left:'15.2rem',top:'1.4rem', overflow:'hidden',opacity:'0.5'}}/>
                <p className=' font-PreSB recommandText'>초보 식물 집사가 <br/> 기르기 좋은 식물 추천</p>
            </div>
        </div> ,
        Expert: 
        <div className="toprecommand">
            {/* 뒤로가기 */}
            <div className="back_button">
            <button onClick={()=>navigate(-1)}style={{position: 'fixed'}}>
            <img src={BackWhite} alt="back_white" style={{width:'4rem',}}/>
            </button>
            </div>
            <div className='ExpertBack'>
                <img src={hardBig} alt="Hard Plant" style={{width:'10rem',position:'absolute',left:'15.7rem', overflow:'hidden', opacity:'0.5'}}/>
                <p className=' font-PreSB recommandText'>경험 있는 식물 집사가 <br/> 기르기 좋은 식물 추천</p>
            </div>
        </div>,
        DarkHouse: 
        <div className="toprecommand">
            {/* 뒤로가기 */}
            <div className="back_button">
            <button onClick={()=>navigate(-1)}style={{position: 'fixed'}}>
            <img src={BackWhite} alt="back_white" style={{width:'4rem',}}/>
            </button>
            </div>
            {/* 상단 화면 */}
            <div className='DarkBack'>
                <img src={dark} alt="Dark Plant" style={{width:'10rem',position:'absolute',left:'15.7rem', overflow:'hidden', opacity:'0.5'}}/>
                <p className=' font-PreSB recommandText'>빛이 잘 들어오지 않아도 <br/> 기르기 좋은 식물 추천</p>
            </div>
        </div>,
        LessWater: 
        <div className="toprecommand">
            {/* 뒤로가기 */}
            <div className="back_button">
            <button onClick={()=>navigate(-1)}style={{position: 'fixed'}}>
            <img src={BackWhite} alt="back_white" style={{width:'4rem'}}/>
            </button>
            </div>
            <div className='WaterBack'>
                <img src={watercan} alt="Less Water Plant" style={{width:'10rem',position:'absolute',top:'1rem',left:'15.7rem', overflow:'hidden', opacity:'0.5'}}/>
                <p className=' font-PreSB recommandText'>물을 조금만 줘도 <br/> 기르기 좋은 식물 추천</p>
            </div>
        </div>,
        
    }[reconame]
    }
  
    </div>

  )
}

export default TopRecommand