'use strict';

const e = React.createElement;

class LikeButton extends React.Component {
  constructor(props) {
    super(props);
    this.state = { 
		liked: false, 
		items: [],
	};
  }

  componentDidMount() {
    fetch('http://localhost:8080/insilicokdd/webresources/py')
        .then(res => res.json())
        .then((result) => {
            this.setState({
                items: result
                //Note: You can set filteredItems here to result as well  
                //if you want it to start out unfiltered
            });
        }
        )
}

  render() {
    return (
    <ol>
      {items.map(reptile => <li>{reptile}</li>)}
    </ol>
  );
  }
}

// Find all DOM containers, and render Like buttons into them.
document.querySelectorAll('.like_button_container')
  .forEach(domContainer => {
    // Read the comment ID from a data-* attribute.
    const commentID = parseInt(domContainer.dataset.commentid, 10);
    ReactDOM.render(
      e(LikeButton, { commentID: commentID }),
      domContainer
    );
});