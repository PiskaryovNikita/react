import React, {Component} from 'react'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import CircularProgress from '@material-ui/core/CircularProgress';


export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            todos: [],
            isLoaded: false,
        }
    }

    componentDidMount = () => {
        this.setState({isLoaded: false});
        fetch('http://localhost:8080/api/todos', {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(res => res.json())
            .then(json => {
                this.setState({
                    todos: json,
                    isLoaded: true
                })
            }).catch(e => console.log(e));
    }

    render() {
        if (this.state.isLoaded) {
            return (
                <Paper>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>id</TableCell>
                                <TableCell>name</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.todos.map((todo, key) =>
                                <TableRow key={key} className="table">
                                    <TableCell>{todo.id}</TableCell>
                                    <TableCell> {todo.name}</TableCell>
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </Paper>
            )
        } else {
            return (
                <div align="center">
                    <CircularProgress/>
                </div>
            )
        }
    }
}