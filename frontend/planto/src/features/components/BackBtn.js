import React from 'react'
import { Navigate, useNavigate } from 'react-router-dom'
import BackWhite from '../../assets/icons/back_white.png'



function BackBtn() {
  const navigate = useNavigate();
  return (
    <div>
    <button onClick={()=>navigate(-1)} style={{position: 'fixed', width:'4rem'}}>
    <img src={BackWhite} alt="back_white"  />
    </button>
    </div>  )
}

export default BackBtn