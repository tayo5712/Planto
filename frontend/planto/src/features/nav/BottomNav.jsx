import React, { useState } from "react";
import './BottomNav.css';
import {Link} from 'react-router-dom';
import { NavLink } from "react-router-dom";

// 아이콘
import { RiLeafLine, RiQrCodeLine, RiBook3Line, RiTrophyFill } from "react-icons/ri";


const BottomNav=()=>{


    return(

        <nav className="wrapper">
            <div className="navbox I1">
                <NavLink to="/main"  style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})}>
                <RiLeafLine className="navIcon" size="27" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})} />
                <p className=" font-PreR" style={{marginTop:'0.2rem'}}>플랜토</p>
                </NavLink>
            </div>
            
            <div className="navbox">
                <NavLink to="/dictionary" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})}>
                <RiBook3Line className="navIcon" size="27" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})} />
                <p className=" font-PreR "  style={{marginTop:'0.2rem'}}>식물 사전</p>
                </NavLink>
            </div>
            
            <div className="navbox">
                <NavLink to="/reward" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4', })} >
                <RiTrophyFill className="navIcon" size="27" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})} />
                <p className="font-PreR" style={{marginTop:'0.2rem'}} >업적</p>
                </NavLink>
            </div>
            
            <div className="navbox" style={{marginLeft:'-0.5rem'}}>
                <NavLink to="/enrollment" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})}>
                <RiQrCodeLine className="navIcon" size="27" style={({isActive})=>({color: isActive ?'#5D9451':'#A4A4A4'})} />
                <p className="font-PreR"style={{marginTop:'0.2rem'}} >등록</p>
                </NavLink>
            </div>


        </nav>
    )
}

export default BottomNav;
