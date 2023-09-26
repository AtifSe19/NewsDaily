import React, { Component } from 'react';

export class UserPanel extends Component {
    constructor(props) {
        super(props);
        this.props = props;
    }
    // state = {  }
    render() { 
        return ( <>
            <div>Hi user</div>
        </> );
    }
}
 
//  default UserPanel;
import { Routes, Route } from 'react-router-dom'

import SideMenu from '../../../components/sideMenu/SideMenu'
import Welcome from '../../../components/welcome/Welcome'
import NewsList from '../newsList/NewsList'
import PendingComments from '../pendingComments/PendingComments'

const UserPanel = (user) => {
  return (
    <div className='panel'>
      <div style={{ width: '15%' }}>
        <SideMenu role={user.role} />
      </div>
      <div style={{ width: '85%', display: 'flex', justifyContent: 'center'}} className='my-5'>
        <Routes>
          <Route path="/" element={<Welcome role={user.role} />} />
          <Route path="/showNews" element={<NewsList />} />
          <Route path="/pendingComments" element={<PendingComments />} />
        </Routes>
      </div>
    </div>
  )
}

export default UserPanel