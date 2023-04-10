import React from 'react'
import { useNavigate } from 'react-router-dom';
import './ModalLike.css'

const ModalLike = (props) => {
  // 열기, 닫기, 모달 헤더 텍스트를 부모로부터 받아옴
  const { open, close, header, Like, Prank,Touch,randomItem, Tips } = props;
  const navigate = useNavigate()


  return (
      // 모달이 열릴때 openModal 클래스가 생성된다.
      <div className={open ? 'openModal modallike ' : 'modallike'}>
        {open ? (
          <section>
            <header className='font-PreL'>
            {props.children}
              <button className="close" onClick={close}>
                &times;
              </button>
            </header>
            <main>
              <div className="likedatabox">
              <span className='likeinfo font-PreM'>호감도 </span> <span className='font-PreSB datalike'>{Like} 💓</span>
              </div>
              <div className="likedatabox">
              <span className='likeinfo font-PreM'>Prank </span> <span className='font-PreSB datalike'>{Prank} 💢</span>  
              </div>
              <div className="likedatabox">
              <span className='likeinfo font-PreM'>Touch </span>  <span className='font-PreSB datalike'>{Touch} 👋</span>
              </div>
            </main>
            <footer>
              <div className="font-PreR">
              {Tips}!
              </div>
            </footer>
          </section>
        ) : null}
      </div>  )
}

export default ModalLike