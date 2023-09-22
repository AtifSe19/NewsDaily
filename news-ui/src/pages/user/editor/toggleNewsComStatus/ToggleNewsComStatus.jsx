import React, { useEffect, useState } from "react";
import { AiTwotoneDelete } from "react-icons/ai";
import { HiCheck } from "react-icons/hi";
import Pagination from "https://cdn.skypack.dev/rc-pagination@3.1.15";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import { useParams } from "react-router-dom";

import "./ToggleNewsComStatus.css";

const ToggleNewsComStatus = () => {
    const { sectionType } = useParams();
    const [newsCom, setNewsCom] = useState([]);
    const [perPage, setPerPage] = useState(10);
    const [size, setSize] = useState(perPage);
    const [current, setCurrent] = useState(1);

    const loadNewsCom = async () => {
        try {
            const response = await axios.get(`/api/v1/${sectionType}/pending`);
            if (Array.isArray(response.data)) {
                setNewsCom(response.data);
            } else {
                console.error("API response is not an array:", response.data);
            }
        } catch (error) {
            console.error("Error loading news articles:", error);
        }
    };

    useEffect(() => {
        loadNewsCom();
    }, [current, size, sectionType]);

    const PerPageChange = value => {
        setSize(value);
        setCurrent(1);
    };

    const deleteNewsCom = async id => {
        try {
            await axios.delete(`/api/v1/${sectionType}/${id}`);
            loadNewsCom();
            toast.success(`${sectionType} discarded successfully!`);
        } catch (error) {
            console.error("Error discarding:", error);
            toast.error("Error discarding");
        }
    };
    const approveNewsCom = async id => {
        try {
            await axios.put(`/api/v1/${sectionType}/approve/${id}`);
            loadNewsCom();
            toast.success(`${sectionType} approved successfully!`);
        } catch (error) {
            console.error("Error approving:", error);
            toast.error("Error approving");
        }
    };

    const PaginationChange = (page, pageSize) => {
        setCurrent(page);
        setSize(pageSize);
    };

    const PrevNextArrow = (current, type, originalElement) => {
        if (type === "prev") {
            return (
                <button>
                    <i className="fa fa-angle-double-left"></i>
                </button>
            );
        }
        if (type === "next") {
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
                                        total={newsCom.length}
                                        current={current}
                                        pageSize={size}
                                        showSizeChanger={false}
                                        itemRender={PrevNextArrow}
                                        onShowSizeChange={PerPageChange}
                                    />
                                </div>
                                <div className="table-responsive">
                                    <table className="table table-text-small mb-0 text-center">
                                        <thead className="thead-primary table-sorting">
                                            <tr>
                                                <th>#</th>
                                                {sectionType && sectionType === "news" && (
                                                    <th>title</th>
                                                )}
                                                <th>content</th>
                                                <th>postedAt</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {newsCom.map(newsCom => (
                                                <tr key={newsCom.id}>
                                                    <td>{newsCom.id}</td>
                                                    {sectionType && sectionType === "news" && (
                                                        <td>newsCom.title</td>
                                                    )}
                                                    <td>{newsCom.content}</td>
                                                    <td>{newsCom.postedAt}</td>
                                                    <td>
                                                        <button
                                                            className="btn"
                                                            onClick={() => approveNewsCom(newsCom.id)}
                                                        >
                                                            {/* Approve */}
                                                            <h4>
                                                                <HiCheck />
                                                            </h4>
                                                        </button>
                                                        <button
                                                            className="btn"
                                                            onClick={() => deleteNewsCom(newsCom.id)}
                                                        >
                                                            {/* Reject */}
                                                            <h4>
                                                                <AiTwotoneDelete />
                                                            </h4>
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
                                        total={newsCom.length}
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

export default ToggleNewsComStatus;
