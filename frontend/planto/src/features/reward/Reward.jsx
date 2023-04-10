import React, { useEffect, useState } from 'react'
import BottomNav from '../nav/BottomNav'
import './Reward.css'
import { HOST } from '../login/OAuth'
import axios from 'axios'
import lock from '../../assets/icons/lock.png'
import { useRef } from 'react'
// 
// 모달
import ModalReward from './ModalReward'
import { useLocation, useNavigate } from 'react-router-dom';

function Reward() {
  const [achievements, setAchievements] = useState()
  const [text, setText] = useState()
  const [countAchievement, setCountAchievement] = useState(0)
  let remainAchievements = useRef([]);

  useEffect(() => {
    const token = window.localStorage.getItem('token');
    axios({
      method: "get",
      url: `${HOST}/api/v1/achievement`,
      headers: {
        Authorization: token
      }
    }).then(function (response) {
      console.log(response)
      remainAchievements.current = []
      setAchievements(response.data)
      setText(response.data.slice(0, 2))
      setCountAchievement(response.data.length)

      for (let i = 0; i < 20 - response.data.length; i++) {
        remainAchievements.current.push(i)
      }
    });
  }, [])



  const user = {
    name: window.localStorage.getItem('username'),
    profileImageUrl: window.localStorage.getItem('profileImageUrl')
  }
  
  return (
    <div style={{ backgroundColor: '#FAF8F8', padding: '1.2rem',height:'100vmax'  }}>
      <div className='reward_top_wrapped'>
        <div className='reward_text'>
          <div className="Myplanto_title">
            <span className="font-PreR"> My</span>
            <span className="font-PreEB"> 업적 </span>
          </div>
          <div className="Myplanto_name">
            <span className="font-PreR"> 반가워요,</span>
            <span className="font-PreEB"> {user.name}</span><span className="font-PreR">님!</span>
          </div>
          <div>
            <p className='font-PreR reward_message'> 업적을 달성해서
              <br></br>컬렉션을 완성해보세요!</p>
          </div>
        </div>

        <div>
          <img className='reward_img' src={user.profileImageUrl} alt="사용자 이미지" />
        </div>
      </div>

      {/* 업적 달성 메세지 */}
      <div style={{marginTop:'1.5rem'}}>
        {
          text ?
            text.map((item) => <Descriptions description={item.achievement.name} key={item.user_achievement_id} />) :
            null
        }
      </div>
      {/* 모달 */}


      {/* 구분선 */}
      <div style={{display:'flex', marginTop:'1rem'}}>
      <div style={{width:'30%',height:'1px', backgroundColor:'black', margin:'auto',marginTop:'1rem'}}></div>
      <span className="font-PreSB rewardsubtitle"> 업적 </span>
      <div style={{width:'30%',height:'1px', backgroundColor:'black', margin:'auto',marginTop:'1rem'}}></div>

      </div>

      {/* 업적 달성 개수 */}
      <div style={{textAlign:'center'}}>
        <div>
          <span className='font-PreSB'>{countAchievement}/</span><span className='font-PreR '>20</span>
        </div>
      </div>
      



      <div className='achievement-frame'>
        {/* 보유한 업적 */}
        {
          achievements ?
            achievements.map((item) => <Achievement achievement={item.achievement} key={item.user_achievement_id} />) :
            null
        }
        {/* 보유하지 않은 업적 */}
        {
          remainAchievements.current.map((item, key) => <RemainAchievement key={key} />)
        }
      </div>
      <BottomNav />
    </div>
  )
}


function Achievement({ achievement }) {

  const [modalOpen, setModalOpen] = useState(false);

  const openModal = () => {
    setModalOpen(true);
    console.log(setModalOpen)
  };
  
  const closeModal = () => {
    setModalOpen(false);
  };

  
  return (
     <span>

        <ModalReward open={modalOpen} close={closeModal} header="이름 확인" 
         name={achievement.name} description={achievement.description} img={achievement.imageName}
         date={achievement.created} 
         >
          <div className="modalText">
            <div className=" likeheader">
              <img className='imgTag' style={{margin:'auto', marginBottom:'1.2rem', width:'5rem', height:'5rem'}}src={`/achievements/${achievement.imageName}.png`} alt={achievement.imageName} />
              <p className='font-PreSB rewardname' style={{ textAlign:'center'}}>
              {achievement.name}
              </p>
              <div style={{width:"3rem", margin:'auto',borderBottom:'2px solid white',marginBottom:'.8rem',marginTop:'.5rem',opacity:'0.5'}}></div>
              <p className="font-PreM rewardsub"style={{color:'#329E5D', textAlign:'center'}}>
              {achievement.description}
              </p>

              <p>
              {achievement.created}
              </p>
            </div>
          </div>
        </ModalReward>

        <span className='achievement-frame' onClick={()=>openModal({achievement})} >
          {/* 모달 */}
          <img className='imgTag' src={`/achievements/${achievement.imageName}.png`} alt={achievement.imageName} />
        </span>
      </span>
  );
}

function RemainAchievement() {
  return (
    <span className='achievement-frame'>
      <img className='imgTag' src={lock} alt="lock" />
    </span>
  );
}

function Descriptions({ description }) {
  return (
    <span>
      <h1 className='font-PreR rewardText'>
      ✔️ '{description}'를 획득했습니다
      </h1>
    </span>
  );
}

export default Reward;