import React from 'react'
import './progressbar.css'

const ProgressContainer = ({like}) => {
	const [style, setStyle] = React.useState({});
	
	setTimeout(() => {
		const newStyle = {
			opacity: 1,
			width: `${like}%`
		}
		
		setStyle(newStyle);
	}, 200);
	
	return (
		<div className="progress">
			<div className="progress-done font-PreL" style={style}>
				{like}%
			</div>
		</div>
	)
}



export default ProgressContainer