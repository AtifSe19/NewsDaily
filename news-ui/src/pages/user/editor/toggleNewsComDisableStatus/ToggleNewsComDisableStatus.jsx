import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Pagination from "https://cdn.skypack.dev/rc-pagination@3.1.15";
import { toast, ToastContainer } from 'react-toastify';
import { BsToggleOn, BsToggleOff } from 'react-icons/bs';

const ToggleNewsComDisableStatus = () => {
  const [newsCom, setNewsCom] = useState([]);
  const [perPage, setPerPage] = useState(10);
  const [size, setSize] = useState(perPage);
  const [current, setCurrent] = useState(1);
  const { sectionType } = useParams();
  const isRequesting = useRef(false); // Use a ref to track request state

  useEffect(() => {
    const loadNewsCom = async () => {
      try {
        if (!isRequesting.current) { // Check if a request is already in progress
          isRequesting.current = true; // Set request to in progress
          const response = await axios.get(`/api/v1/${sectionType}/editor`);
          setNewsCom(response.data);
          isRequesting.current = false; // Reset request state when request is complete
        }
      } catch (error) {
        console.error('Error fetching search results:', error);
        isRequesting.current = false; // Reset request state in case of an error
      }
    };

    loadNewsCom();
  }, [current, size, sectionType]);

  const toggleDisableNews = async (id, isDisabled) => {
    try {
      if (!isRequesting.current) { // Check if a request is already in progress
        isRequesting.current = true; // Set request to in progress
        await axios.put(`/api/v1/${sectionType}/disable/${id}`);
        setNewsCom((prevNewsCom) =>
          prevNewsCom.map((item) =>
            item.id === id ? { ...item, isDisabled: !isDisabled } : item
          )
        );

        // Show a toast message based on the new status
        if (!isDisabled) {
        //   toast.success(`${sectionType} disabled successfully!`);
        } else {
        //   toast.success(`${sectionType} enabled successfully!`);
        }
        isRequesting.current = false; // Reset request state when request is complete
      }
    } catch (error) {
      console.error('Error toggling:', error);
      toast.error('Error toggling');
      isRequesting.current = false; // Reset request state in case of an error
    }
  };

  const PerPageChange = (value) => {
    setSize(value);
    setCurrent(1);
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
                        {sectionType && sectionType === 'news' && (
                          <th>title</th>
                        )}
                        <th>content</th>
                        <th>postedAt</th>
                        <th>Enable / Disable</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      {newsCom.map((newsCom) => (
                        <tr key={newsCom.id}>
                          <td>{newsCom.id}</td>
                          {sectionType && sectionType === 'news' && (
                            <td>{newsCom.title}</td>
                          )}
                          <td>{newsCom.content}</td>
                          <td>{newsCom.postedAt}</td>
                          <td>
                            <button
                              className="btn"
                              onClick={() => toggleDisableNews(newsCom.id, newsCom.isDisabled)}
                            >
                              {newsCom.isDisabled ? (
                                <h4>
                                  <BsToggleOff />
                                </h4>
                              ) : (
                                <h4>
                                  <BsToggleOn />
                                </h4>
                              )}
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

export default ToggleNewsComDisableStatus;
