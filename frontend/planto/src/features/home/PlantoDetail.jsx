import React, { useEffect, useState } from 'react'
import BottomNav from '../nav/BottomNav';
// 이미지
import back from '../../assets/background/detailback.png'
import temp from '../../assets/icons/temp.png'
import humid from '../../assets/icons/humid.png'
import lux from '../../assets/icons/lux.png'
import pre from '../../assets/icons/back_white.png'
// 이미지2
import nowtem from '../../assets/icons/nowtem.png'
import nowhum from '../../assets/icons/nowhum.png'
import nowsun from '../../assets/icons/nowsun.png'


//  api주소
import { HOST } from "../login/OAuth"
import axios from 'axios';

import './PlantoDetail.css'
import ProgressContainer from '../components/Progressbar';
// 모달
import ModalLike from '../components/ModalLike'
import { useLocation, useNavigate } from 'react-router-dom';

function PlantoDetail() {
  // potID
  const location = useLocation();
  const plantId = location.state.plantId
  const navigate = useNavigate();


  // palnt ID 받아오기


  // plant 받아오기
    const [plant, setPlant] = useState([]);
    const [character, setCharacter] = useState("");
    const [plantoAd, setPlantoAd] = useState("");
    const [tips ,setTips] = useState('');
    const [plantDict, setPlantDict] = useState("");
    // 식물 데이터 받아오기!
    const [plantdetail, setPlantDetail] = useState([]);
    
    const token = window.localStorage.getItem('token');

    // DB에 저장된 데이터 받아오기
    useEffect(() => {
      // 실시간 데이터 받아오기
      function getPlantData(){
        
        axios({
            method: "get",
            //
            url: `${HOST}/api/v1/plant/${plantId}`,
            headers: {
              Authorization: token,
            },
        }).then((response) => {
            setPlant(response.data)
            setPlantDict(response.data.plant_dict_plant_dict_id)
            console.log(response.data.liking)
            console.log(response.data)
            
            // 호감도
            if( response.data.liking >=70){
              setCharacter("LOVELY PLANTO")
              setPlantoAd("Lovely Planto는 사랑이 가득합니다🥰")}
            else if ( 70 >  response.data.liking && response.data.liking  >= 30   ){
              setCharacter("GOOD PLANTO")
              setPlantoAd("Good Planto와 호감도를 쌓아보세요😄")}
            else{
              setCharacter("BAD PLANTO")  
              setPlantoAd("Bad Planto는 조금 까칠합니다😬")
            }
        }).catch((e) =>{
          console.log(e)
        });
      }

      const TIP = ['Touch는 호감도를 올려줘요!','플랜토의 성향에 따라 기본 표정이 달라져요','LOVELY 플랜토는 사랑이 가득해요','플랜토의 다양한 표정을 모아보세요'];
      setTips(TIP[Math.floor(Math.random() * 4)])

      getPlantData();
      const getData = setInterval(() => getPlantData(), 2000);
      return () => {
        clearInterval(getData);
      }
  }, [token])
  
  useEffect(() => {
    axios({
      method: "get",
      url: `${HOST}/api/v1/dict/detail/${plantDict}`,
      headers: {
        Authorization: token,
      },
  }).then(function (response) {
      setPlantDetail(response.data)
  });

  }, [plantDict, token])
  

  // 호감도
  const like = plant.liking
    
  // 날짜 계산
  const dday = new Date(`${plant.createDate}`)
  const [days, setDays] = useState(0);

  useEffect(()=>{
    const today =  new Date();
    const gapNum = (dday - today)*-1;
    setDays(Math.ceil(gapNum/(1000*60*60*24)))
    
  },[dday])
  
 // 호감도 모달
  const [modalOpen, setModalOpen] = useState(false);
  const openModal = () =>{
    setModalOpen(true);
    console.log(setModalOpen)  }
  const closeModal = () => {
      setModalOpen(false);
    };
  
  return (
    <div className="plantodetaildiv"  >
      <div className="detailback"style={{width:'100vw',paddingBottom:'12rem',height:
      '100vmax',
padding:'1rem', backgroundImage:`url("${back}")`,backgroundSize:'cover',
}}>
        <div>
          {/* 상단 */}
          <div>
            <button onClick={()=>navigate(-1)}style={{position:'fixed',top:'1%', left:'1%'}}>
            <img src={pre} alt="back" style={{width:"4rem"}} />
            </button>
          </div>
          <div>
            {/* 이미지 */}
            <div style={{
            width:'10rem',
            height:'10rem',
            marginLeft:'7rem',
            borderRadius:'15rem',
            backgroundSize:'cover',
            backgroundPosition:'center',
            backgroundImage: `url("https://firebasestorage.googleapis.com/v0/b/planto-e2910.appspot.com/o/${plant.imagePath}?alt=media")`
            }}>
            </div>
            {/* <img src={`https://firebasestorage.googleapis.com/v0/b/planto-e2910.appspot.com/o/${plant.imagePath}?alt=media`}/> */}
            <div className='plantoDetailName font-PreSB'> {plant.name}</div>
            <div className='plantoDetailName2 font-PreSB'> {plantdetail.name} </div>
          </div>
          {/* 호감도 표현 */}
          <div className='likeboxwrap' style={{marginBottom:'1rem'}}>
              <div className='likebox'>
                <div className='font-PreM'
                style={{color:'white', textAlign:'center'}}
                > 🤍 우리 함께한지 {days}일 🤍</div>
              </div>
          <div>
          {/* 호감도 모달 */}
          <ModalLike open={modalOpen} close={closeModal} header="이름 확인" Like={like} Prank={plant.attack} Touch={plant.touch} character={character} Tips={tips}>
            <div className="modalText">
        
              <div className="font-PreL likeheader">
                <p className='font-PreM' style={{fontSize:"1.3rem"}}>
                플랜토 {plant.name}는
                </p>
                <p>
                <span className='font-PreEB plantochar'>{character}</span>
                <span className='font-PreM'> 입니다.</span>
                </p>
                <p style={{color:'#A1F5B1'}}>
                {plantoAd}
                </p>
              </div>
              
          </div>
          </ModalLike>
          {/* 호감도 박스 */}
          <div className='font-PreM plantodetailsubtitle'>  
            플랜토와 얼마나 친해졌나요? </div>
            <div style={{position:'absolute', top:'4.2rem',left:'3rem'}}>
              <ProgressContainer 
              like ={like}
              />
            </div>
            <div style={{position:'absolute', top:'5rem', left:'16rem'}}>
            <button className="font-PreM likecheckbtn" onClick={()=>openModal()}> 더 보기 </button>
            </div>
          </div>
              
          </div>
          {/*  현재 상태 */}
          <div>
                <p className='font-PreR infonowtitle '> 현재 식물의 상태를 확인하세요 </p>
                <div className="infobox3">
                    <div className='infonowcondition'>
                        <p className='font-PreSB infotitle' style={{marginTop:'0.15rem'}}>온도</p>
                        <img src={nowtem} alt="temp" className='infoIcon2'></img>
                        <p className='font-PreSB infotext2' style={{marginTop:'0.15rem'}}>{plant.temperature}°C</p>
                    </div>
                    <div className='infonowcondition'>
                        <p className='font-PreSB infotitle' style={{marginTop:'0.15rem'}}>습도</p>
                        <img src={nowhum} alt="humid" className='infoIcon2'></img>
                        <p className='font-PreSB infotext2 ' style={{marginTop:'0.15rem'}}>{plant.humidity}%</p>
                    </div>
                    <div className='infonowcondition'>
                        <p className='font-PreSB infotitle' style={{marginTop:'0.15rem'}}>조도</p>
                        <img src={nowsun} alt="lux" className='infoIcon2'></img>
                        <p className='font-PreSB infotext2' style={{marginTop:'0.15rem'}}>{plant.sun}(lux)</p>
                    </div>
                </div>
            </div>

          {/* 선호 환경 */}
        
          <div>
            {/* 저장된 식물 ID를 토대로 디테일 구현 */}
            <div>
                <p className='font-PreR infonowtitle'>식물이 원하는 환경이에요</p>
                <div className="infobox" style={{marginBottom:'10rem'}}>
                    <div className='infocondition'>
                        <img src={temp} alt="temp" className='infoIcon'></img>
                        <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>온도</p>
                        <p className='font-PreM infotext' style={{marginLeft:'5rem',marginTop:'0.15rem'}}>{plantdetail.temperatureMin}°C~{plantdetail.temperatureMax}°C</p>
                    </div>
                    <div className='infocondition'>
                        <img src={humid} alt="humid" className='infoIcon'></img>
                        <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>습도</p>
                        <p className='font-PreM infotext' style={{marginLeft:'5rem',marginTop:'0.15rem'}}>{plantdetail.humidityMin}%~{plantdetail.humidityMax}%</p>
                    </div>
                    <div className='infocondition'>
                        <img src={lux} alt="lux" className='infoIcon'></img>
                        <p className='font-PreM infotext' style={{marginLeft:'0.4rem',marginTop:'0.15rem'}}>조도</p>
                        <p className='font-PreM infotext' style={{marginLeft:'4.5rem',marginTop:'0.15rem'}}>{plantdetail.lightMin}~{plantdetail.lightMax}(lux)</p>
                    </div>
                </div>
            </div>

          </div>
          {/* 기능성 정보 */}
          <div style={{height:'.8rem'}}>
          </div>

        </div>
      </div>
      <BottomNav/>
    </div>
  )
}

export default PlantoDetail