import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import BottomNav from '../nav/BottomNav';
import Example from '../../assets/background/backimg.png'

//  api주소
import { HOST } from "../login/OAuth"
import axios from 'axios';

import godetail from '../../assets/icons/godetail.png'
import question from'../../assets/icons/question.png'


function MainPlanto({plantos}) {
  const  userName = window.localStorage.getItem('username')
  const  serialnumber = window.localStorage.getItem('potSerial')
  const navigate = useNavigate();

  
  // plant 받아오기
  const [plant, setPlant] = useState([]);
  const [character, setCharacter] = useState("");
  const [plantoAd, setPlantoAd] = useState("");
  const [tips ,setTips] = useState('');
  const [plantDict, setPlantDict] = useState("");
  // 식물 데이터 받아오기!
  const [plantdetail, setPlantDetail] = useState([]);
  
  const token = window.localStorage.getItem('token');


// 등록된 식물 데이터 받아오기
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
const like = plant?.liking

// potID를 넣어서 plantenroll로 이동
function enrollPlant (potId){
  navigate('/enrollment/plant', { state :{ potId : potId}})
}

const plantoList = plantos.map((planto)=>{
  return(
    
      <div key={planto?.potId}>
      
      <div>
        {console.log(planto)}
        { planto.plant !== null 
        
        ? 
        <div className="PlantoCard">
          {/* 이미지 */}
          <div style={{
          width:'5rem',
          height:'5rem',
          marginLeft:'.3rem',
          borderRadius:'15rem',
          backgroundSize:'cover',
          backgroundPosition:'center',
          backgroundImage: `url("https://firebasestorage.googleapis.com/v0/b/planto-e2910.appspot.com/o/${planto.plant?.imagePath}?alt=media")`
          }}>
          </div>
          {/* 텍스트 */}
          <div style={{width:'10rem',marginLeft:'.5rem'}}>
            <div className='font-PreSB plantoname'> {planto.plant?.name}</div>
            <div className=' font-PreR plantsubname'> {planto.plant.plantDict?.name} </div>

          </div>
          {/* 버튼 */}
          <Link to={`/main/planto/${planto?.potId}`} state={{plantId:planto.plant?.plantId}}>
            <div>    
              <img src={godetail} style={{width:'3.2rem', marginRight:"1rem"}}></img>
            </div>
          </Link>
        </div>
        : 
        
        <div className="PlantoCard2">
            <div>
            <img src={question} style={{width:'7.7rem', marginLeft:'-.7rem'}}></img>
        
            </div>
            <div style={{ marginLeft:'-.7rem'}}>
              <span className='font-PreM'> 
              <p className='font-PreSB nonplanttext' > 플랜토에 식물이 없습니다.</p>
              <p className='font-PreL nonplantsubtext'>식물을 등록하고 소통을 시작하세요. </p>
              <div className="enrollbtn"onClick={() => enrollPlant(planto.potId)}> 
                <p className='font-PreL enrollbtntext ' style={{textAlign:'center'}}>
                등록하기
                </p>
              </div>
              </span>
            </div>
        </div>
        }
    </div>
    
   
  </div>
  )
})

  return (
    
    <div>
      {plantoList}
      <button className="main_add_btn font-PreSB " onClick={() => {navigate("/enrollment");}}> + </button>   
    </div>
  )
}

export default MainPlanto