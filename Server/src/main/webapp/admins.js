var AdminsBox = React.createClass({
    getInitialState: function() {
        return {adminsList: []};
    },
    componentDidMount: function(){
         $.ajax({
                url: 'myUrlServlet',
                type: 'POST',
                data: "function=getAdmins",
                dataType: "json",
                success: function(data){
                    this.setState({adminsList: data});
                }
            });
    },
    setAdminsArray: function(data) {
        this.setState({adminsList: data});
    },
    render: function() {

        return (
            <div>
                <AdminsContentBox adminsList={this.state.adminsList}/>
            </div>
        );
}});


window.AdminsBoxRender = ReactDOM.render(
    <AdminsBox />,
    document.getElementById('adminsContent')
);