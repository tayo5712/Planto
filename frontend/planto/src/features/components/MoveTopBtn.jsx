import React from 'react'
import TopBtn from "../../assets/icons/topbtn.png"
import './MoveTopBtn.css'




function MoveTopBtn() {
const MoveToTop=()=>{
    window.scrollTo({top:0, behavior:'smooth'})  
    }

  return (
    <div className='warrap_btn'>
        <button onClick={MoveToTop}>
            <img src={TopBtn} alt="TopBtn" />
        </button>
    </div>
  )
}

export default MoveTopBtn