import React from 'react'
import { useNavigate } from 'react-router-dom';
import './ModalReward.css'

const ModalReward = (props) => {
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close,date } = props;
  const navigate = useNavigate()


  return (
      // 모달이 열릴때 openModal 클래스가 생성된다.
      <div className={open ? 'openModal modalreward ' : 'modalreward'}>
        {open ? (
          <section>
            <header>
              <div>
                <p className='font-PreM' style={{marginLeft:'5.3rem', fontSize:'1.4rem', color:'white'
                ,width:'8rem', backgroundColor:'#329E5D',borderRadius:'15rem'
              }}>  획득 완료 </p>
              </div>
              <button className="close" onClick={close}>
                &times;
              </button>
            </header>
            <main>
              {props.children}
              {date}
            </main>

          </section>
        ) : null}
      </div>  )
}

export default ModalReward