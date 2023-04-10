import React from 'react'
import {useLocation, useNavigate} from "react-router-dom"
import './PlantDetail.css'
// 이미지 파일
import temp from '../../assets/icons/temp.png'
import humid from '../../assets/icons/humid.png'
import lux from '../../assets/icons/lux.png'
import BackG from '../../assets/icons/back_g.png'
// nav바
import BottomNav from '../nav/BottomNav';
import PlantCarousel from './PlantCarousel'


function PlantDetail() {
  const location = useLocation();
  const plant = location.state.plant;
  const plantName = location.state.plantName;
  const plantNameSecond = location.state.plantNameSecond;
  const navigate = useNavigate();

  return (
    <div style={{backgroundColor:'#FAF8F8', padding:'2.7rem', paddingBottom:'10rem'}}>
        <div>
            <button onClick={()=>navigate(-1)}style={{position:'fixed',top:'1%', left:'1%'}}>
            <img src={BackG} alt="back_green" style={{width:"4rem"}} />
            </button>
        </div>
        <div className='PlantDetailImg'>
            <div style={{
                margin:'auto',
                marginBottom:'0.5rem',
                width:'10rem',
                height:'10rem',
                borderRadius:'15rem',
                backgroundSize:'cover',
                backgroundPosition:'center',
                backgroundImage: `url("https://www.nongsaro.go.kr/cms_contents/301/${plant.imagePath}")`,
                }}
            >
            </div>
        </div>
        <div>
            <p className='font-PreR plantName' >{plantName}</p>
            <p className='font-PreSB plantNameSecond'>{plantNameSecond}</p>
            <p className='font-PreL plantNameEng' >{plant.plantEng}</p>
        </div>

        <div>
            <div className="Line"></div>
        </div>

        {/* 선호 환경 */}
        <div>
            <p className='font-PreSB infoTitle'>최적 환경</p>
            <div className="infobox">
                <div className='infocondition'>
                    <img src={temp} alt="temp" className='infoIcon'></img>
                    <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>온도</p>
                    <p className='font-PreM infotext' style={{marginLeft:'5rem',marginTop:'0.15rem'}}>{plant.temperatureMin}°C~{plant.temperatureMax}°C</p>
                </div>
                <div className='infocondition'>
                    <img src={humid} alt="humid" className='infoIcon'></img>
                    <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>습도</p>
                    <p className='font-PreM infotext' style={{marginLeft:'5rem',marginTop:'0.15rem'}}>{plant.humidityMin}%~{plant.humidityMax}%</p>
                </div>
                <div className='infocondition'>
                    <img src={lux} alt="lux" className='infoIcon'></img>
                    <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>조도</p>
                    <p className='font-PreM infotext' style={{marginLeft:'4.5rem',marginTop:'0.15rem'}}>{plant.lightMin}~{plant.lightMax}(lux)</p>
                </div>
            </div>
        </div>
        {/* 관리방법 */}
        <div>
            <p className='font-PreSB infoTitle'>TIP</p>
            <PlantCarousel/>
        </div>

        <BottomNav/>
    </div>
  )
}

export default PlantDetail