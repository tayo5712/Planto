import React,{ useState, useRef, useMemo}from 'react'
import {useLocation} from "react-router-dom"
// carousel 라이브러리
import Slider from 'react-slick'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
// 이미지
import NotReady from "../../assets/icons/notready.png"

import './PlantCarousel.css'


const PlantCarousel= ()=> {
    // 식물 정보 불러오기
    const location = useLocation();
    const plant = location.state.plant;
    // carousel setting
    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        pauseOnHover: true

      };
    // 
    // 더보기 버튼
    const [isShowMore, setIsShowMore] = useState(false);
    // 글자수 제한
    const textLimit = useRef(60);
    const functionInfo = useMemo(()=>{
        const shortReview = plant.functionInfo.slice(0, textLimit.current);

        if (plant.functionInfo.length > textLimit.current){
            if (isShowMore) {return plant.functionInfo;}
            return shortReview;
        }
        return plant.functionInfo;
    }, [isShowMore])
      
    const manageInfo = useMemo(()=>{
        const shortReview = plant.manageInfo.slice(0, textLimit.current);

        if (plant.manageInfo.length > textLimit.current){
            if (isShowMore) {return plant.manageInfo;}
            return shortReview;
        }
        return plant.manageInfo;
    }, [isShowMore])
      
    const adviceInfo = useMemo(()=>{
        const shortReview = plant.adviceInfo.slice(0, textLimit.current);

        if (plant.adviceInfo.length > textLimit.current){
            if (isShowMore) {return plant.adviceInfo;}
            return shortReview;
        }
        return plant.adviceInfo;
    }, [isShowMore])
      


  return (
    <div style={{width:'326px'}}>
        <Slider {...settings}>
        <div>
           { plant.manageInfo !=="" 
            ?<div className='infobox2 moreinfo'>
                <div className='font-PreM carouseltitle'> 관리 방법 </div>
                <div>
                    <div className='infoDetail font-PreL'>{manageInfo} <span>{(plant.manageInfo.length> textLimit.current) && (isShowMore ? null :'...')}</span> </div>
                    <div className="font-PreM morebtn"onClick={()=>setIsShowMore(!isShowMore)}>{(plant.manageInfo.length> textLimit.current) && (isShowMore ? '닫기':'더보기')}</div>
                </div>    
            </div>
            :
            <div className='infobox2 '>
                <img src={NotReady} alt="Not Ready" style={{width:'3rem', margin:'auto',}} />
                <p className='font-PreL text-stone-700' style={{margin:'auto',textAlign:'center',marginTop:'0.5rem'}}> 관리 정보가 준비중 입니다. </p>
            </div>
            }
        </div>
        <div>
            { plant.functionInfo !== ""
            ?<div className='infobox2 moreinfo '>
                <div className='font-PreM carouseltitle'> 식물 정보 </div>
                <div>
                    <div className='infoDetail font-PreL'>{functionInfo} <span>{(plant.functionInfo.length> textLimit.current) && (isShowMore ? null:'...')}</span> </div>
                    <div>
                    { plant.functionInfo.length > textLimit.current ?<div className="font-PreM morebtn" onClick={()=>setIsShowMore(!isShowMore)}>{(plant.functionInfo.length> textLimit.current) && (isShowMore ? '닫기':'더보기')}</div> : null } 
                    </div>
                </div> 
            </div>
            :<div className='infobox2'>
                <img src={NotReady} alt="Not Ready" style={{width:'3rem', margin:'auto',}} />
                <p className='font-PreL text-stone-700' style={{margin:'auto',textAlign:'center',marginTop:'0.5rem'}}> 식물 정보가 준비중 입니다. </p>
             </div>
            }       
        </div>

        <div>
            { plant.adviceInfo !=="" 
            ?<div className='infobox2 moreinfo '>
                <div className='font-PreM carouseltitle'> 특징 </div>
                <div>
                    <div className='infoDetail font-PreL'>{adviceInfo} <span>{(plant.adviceInfo.length> textLimit.current) && (isShowMore ? null:'...')}</span></div>
                    <div>
                        { plant.adviceInfo.length > textLimit.current ?<div className="font-PreM morebtn" onClick={()=>setIsShowMore(!isShowMore)}>{(plant.adviceInfo.length> textLimit.current) && (isShowMore ? '닫기':'더보기')}</div> : <p className='nomore'></p> } 
                    </div>                </div>  
            </div>
            :<div className='infobox2'>
                <img src={NotReady} alt="Not Ready" style={{width:'3rem', margin:'auto',}} />
                <p className='font-PreL text-stone-700' style={{margin:'auto',textAlign:'center',marginTop:'0.5rem'}}> 특징 정보가 준비중 입니다. </p>
            </div>
            }
        </div>
        </Slider>
    </div>
  )
}

export default PlantCarousel