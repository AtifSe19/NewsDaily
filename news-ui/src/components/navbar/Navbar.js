import React from 'react';

// import './Navbar.css';

import Navbar from './Navbar.module.css';


import { NavLink } from "react-router-dom"; // Import BrowserRouter

//Pages
const Home = () => {
    return (
        <div>
            <h1>Home</h1>
        </div>
    );
};


function NavBar() {
    const [click, setClick] = React.useState(false);

    const handleClick = () => setClick(!click);
    const Close = () => setClick(false);

    return (
        <div>
            <div className={click ? `${Navbar.mainContainer}` : ""} onClick={() => Close()} />
            <nav className={Navbar.navbar}  onClick={e => e.stopPropagation()}>
                <div className= {Navbar.navcontainer}>
                    <NavLink exact to="/" className= {Navbar.navlogo}
                        //style={{ fontFamily: 'ClashDisplay-Regular, sans-serif' }}
                    >
                        Thynk News
                        <>{' '}</>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-tv" viewBox="0 0 16 16" color='#ffdd40'>
                            <path d="M2.5 13.5A.5.5 0 0 1 3 13h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zM13.991 3l.024.001a1.46 1.46 0 0 1 .538.143.757.757 0 0 1 .302.254c.067.1.145.277.145.602v5.991l-.001.024a1.464 1.464 0 0 1-.143.538.758.758 0 0 1-.254.302c-.1.067-.277.145-.602.145H2.009l-.024-.001a1.464 1.464 0 0 1-.538-.143.758.758 0 0 1-.302-.254C1.078 10.502 1 10.325 1 10V4.009l.001-.024a1.46 1.46 0 0 1 .143-.538.758.758 0 0 1 .254-.302C1.498 3.078 1.675 3 2 3h11.991zM14 2H2C0 2 0 4 0 4v6c0 2 2 2 2 2h12c2 0 2-2 2-2V4c0-2-2-2-2-2z" />
                        </svg>
                    </NavLink>
                    <ul className={click ? `${Navbar.navmenu} ${Navbar.active}` : Navbar.navmenu}>

                    {/*<ul className={click ? "Navbar navmenu active" : "Navbar navmenu"}>*/}
                        <li className={Navbar.navitem}>
                            <NavLink
                                exact
                                to="/"
                                activeClassName={Navbar.active}
                                className= {Navbar.navlinks}
                                onClick={click ? handleClick : null}
                            >
                                Login
                            </NavLink>
                        </li>

                    </ul>
                    <div className={Navbar.navicon} onClick={handleClick}>
                        {/*<ul className={click ? `${Navbar.navmenu} ${Navbar.active}` : Navbar.navmenu}>*/}
                        <i className={click ? "fa fa-times" : "fa fa-bars"}></i>
                    </div>
                </div>
            </nav>
        </ div>
    );
}
export default NavBar;