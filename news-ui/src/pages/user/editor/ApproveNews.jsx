import React from "react";
import { useState, useEffect } from "react";
import Pagination from "https://cdn.skypack.dev/rc-pagination@3.1.15";
import './SearchUser.css';
import axios from "axios";
import { AiTwotoneDelete, AiTwotoneEdit } from 'react-icons/ai';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Link } from 'react-router-dom';

const SearchUser = () => {
    const [newscom, setNews] = useState([]);
    const [perPage, setPerPage] = useState(10);
    const [size, setSize] = useState(perPage);
    const [current, setCurrent] = useState(1);

    useEffect(() => {
        loadUsers();
    }, [current, size]);

    const loadNews = async () => {
        try {
            const response = await axios.get('/api/v1/newscom/pending');
            if (Array.isArray(response.data)) {
                setNews(response.data);
            } else {
                console.error('API response is not an array:', response.data);
            }
        } catch (error) {
            console.error('Error loading newscom articles:', error);
        }
    };

    const PerPageChange = (value) => {
        setSize(value);
        setCurrent(1);
    };

    const discardNews = async (id) => {
        try {
            await axios.delete(`/api/v1/newscom/${id}`);
            loadUsers();
            toast.success('Discarded successfully!');
        } catch (error) {
            console.error('Error discarding:', error);
            toast.error('Error discarding');
        }
    };

    const approveNews = async (id) => {
        try {
            await axios.put(`/api/v1/newscom/approveNewsToggle/${id}`);
            loadNews();
            toast.success('Approved successfully!');
        } catch (error) {
            console.error('Error approving:', error);
            toast.error('Error approving');
        }
    };

    const getData = () => {
        const filteredNews = newscom.filter((newscom) => newscom.isApproved === false);
        return filteredNews.slice((current - 1) * size, current * size);
    };

    const PaginationChange = (page, pageSize) => {
        setCurrent(page);
        setSize(pageSize);
    };

    const PrevNextArrow = (current, type, originalElement) => {
        if (type === 'prev') {
            return (
                <button>
                    <i className="fa fa-angle-double-left"></i>
                </button>
            );
        }
        if (type === 'next') {
            return (
                <button>
                    <i className="fa fa-angle-double-right"></i>
                </button>
            );
        }
        return originalElement;
    };

    return (
        <>
            <div className="container-fluid mt-5 mb-5">
                <div className="row justify-content-center">
                    <div className="col-md-10">
                        <div className="card">
                            <div className="card-body p-0">
                                <div className="table-filter-info">
                                    <Pagination
                                        className="pagination-data"
                                        showTotal={(total, range) =>
                                            `Showing ${range[0]}-${range[1]} of ${total}`
                                        }
                                        onChange={PaginationChange}
                                        total={getData().length} // Use the filtered data length as total count
                                        current={current}
                                        pageSize={size}
                                        showSizeChanger={false}
                                        itemRender={PrevNextArrow}
                                        onShowSizeChange={PerPageChange}
                                    />
                                </div>
                                <div className="table-responsive">
                                    <table className="table table-text-small mb-0">
                                        <thead className="thead-primary table-sorting">
                                            <tr>
                                                <th>#</th>
                                                <th>Title</th>
                                                <th>Content</th>
                                                <th>postedAt</th>
                                                <th>isApproved</th>
                                                <th>isDisabled</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {getData().map((newscom) => (
                                                <tr key={newscom.id}>
                                                    <td>{newscom.id}</td>
                                                    <td>{newscom.Title}</td>
                                                    <td>{newscom.password}</td>
                                                    <td>{newscom.email}</td>
                                                    <td>
                                                        <Link to={`/editUser/${user.id}`}>
                                                            <button className='btn' >
                                                                <h4><AiTwotoneEdit /></h4>
                                                            </button>
                                                        </Link>
                                                        <button className='btn' onClick={() => deleteUser(user.id)}>
                                                            <h4><AiTwotoneDelete /></h4>
                                                        </button>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                                <div className="table-filter-info">
                                    <Pagination
                                        className="pagination-data"
                                        showTotal={(total, range) =>
                                            `Showing ${range[0]}-${range[1]} of ${total}`
                                        }
                                        onChange={PaginationChange}
                                        total={getData().length} // Use the filtered data length as total count
                                        current={current}
                                        pageSize={size}
                                        showSizeChanger={false}
                                        itemRender={PrevNextArrow}
                                        onShowSizeChange={PerPageChange}
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <ToastContainer />
        </>
    );
};

export default SearchUser;
