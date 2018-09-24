//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 24 "gramaticaHH.Y"
package Parser;

//#line 24 "Parser.java"

import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;





public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character
    private ArchController lexico;//LA CREAMOS NOSOTROS

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,    4,    4,    7,    7,
    8,    6,    6,    5,    5,    5,    5,   11,    9,   13,
   13,   14,   15,   15,   10,   12,   17,   17,   17,   18,
   18,   18,   19,   19,   19,   19,   16,   20,   20,   20,
   20,   20,   20,
};
final static short yylen[] = {                            2,
    1,    1,    1,    4,    1,    1,    2,    1,    3,    1,
    8,    1,    1,    1,    1,    1,    1,    5,    8,    2,
    1,    4,    1,    1,    8,    4,    3,    3,    1,    3,
    3,    1,    1,    2,    1,    1,    3,    1,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   13,   12,    0,    0,    0,    0,    1,
    2,    3,    5,    6,    0,    8,   14,   15,   16,   17,
    0,    0,    0,    0,    0,    0,   10,    0,   33,   35,
   36,    0,    0,    0,   32,    0,    0,    0,    0,    0,
    0,    0,   34,   26,    0,    0,    0,    0,    0,   41,
   42,   43,   38,   39,   40,    0,    0,    0,    0,    4,
    9,    0,    0,   30,   31,    0,    0,    0,   18,    0,
    0,    0,   23,   24,    0,   21,    0,    0,    0,    0,
   20,    0,   25,   11,   19,    0,   22,
};
final static short yydgoto[] = {                          9,
   10,   11,   12,   13,   14,   15,   28,   16,   17,   18,
   19,   20,   75,   76,   77,   36,   33,   34,   35,   56,
};
final static short yysindex[] = {                      -121,
 -262,  -14, -244,    0,    0,    3,   19, -121,    0,    0,
    0,    0,    0,    0, -204,    0,    0,    0,    0,    0,
  -45,  -45,   21, -201, -200, -223,    0,    4,    0,    0,
    0, -194,   11,  -20,    0,   26,  -24,   28,   29,   30,
  -53, -191,    0,    0,  -45,  -45,  -45,  -45, -121,    0,
    0,    0,    0,    0,    0,  -45,  -49,   31,  -47,    0,
    0,  -20,  -20,    0,    0, -190,    7, -121,    0, -229,
 -121,  -44,    0,    0, -122,    0, -189, -186, -195,   39,
    0, -121,    0,    0,    0,   40,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,  -37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -32,  -27,    0,    0,    0,   44,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -3,    0,   60,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,    0,    0,  -12,   -6,   10,    0,
};
final static int YYTABLESIZE=277;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
    7,    8,   80,   29,   26,   29,   29,   29,   27,   37,
   27,   27,   27,   28,   21,   28,   28,   28,   45,   23,
   46,   47,   29,   29,   29,   22,   48,   27,   27,   27,
   73,   74,   28,   28,   28,   53,   55,   54,   62,   63,
    1,    2,   24,   67,    7,   66,    3,    4,    5,   45,
    6,   46,    7,   45,   44,   46,   64,   65,   25,   27,
   38,   39,   42,   40,   72,   43,   49,   78,   57,   58,
   59,   60,   61,   68,   69,   70,   71,   84,   86,   82,
   79,   83,   85,   87,   37,   41,   81,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,   74,    0,
    0,    0,    1,    2,    0,    0,    0,    0,    3,    4,
    5,    0,    6,    0,    7,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   29,   30,    0,    0,   31,   29,
   29,   29,    0,    0,   27,   27,   27,    0,    0,   28,
   28,   28,   50,   51,   52,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    7,    7,    0,    7,    7,    0,
    7,    7,    7,    0,    7,    0,    7,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,  123,  125,   41,    8,   43,   44,   45,   41,   22,
   43,   44,   45,   41,  277,   43,   44,   45,   43,  264,
   45,   42,   60,   61,   62,   40,   47,   60,   61,   62,
  260,  261,   60,   61,   62,   60,   61,   62,   45,   46,
  264,  265,   40,   56,   44,   49,  270,  271,  272,   43,
  274,   45,  276,   43,   44,   45,   47,   48,   40,  264,
   40,  263,   59,  264,   68,  260,   41,   71,   41,   41,
   41,  125,  264,  123,   44,  123,  267,  273,   82,  269,
  125,  268,   44,   44,   41,   26,   75,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  260,  261,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,   -1,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  260,  261,   -1,   -1,  264,  257,
  258,  259,   -1,   -1,  257,  258,  259,   -1,   -1,  257,
  258,  259,  257,  258,  259,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  264,  265,   -1,  267,  268,   -1,
  270,  271,  272,   -1,  274,   -1,  276,
};
}
final static short YYFINAL=9;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","\":=\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque_sentencias",
"bloque_sentencias : bloque_lista_sentencias",
"bloque_sentencias : sentencia",
"bloque_lista_sentencias : '{' bloque_sentencias sentencia '}'",
"sentencia : declaracion",
"sentencia : ejecucion",
"declaracion : tipo lista_de_variables",
"declaracion : clousure",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')' ','",
"control : CASE '(' ID ')' '{' lista_acciones '}' ','",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte DO bloque_sentencias ','",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"seleccion : IF '(' condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF",
"asignacion : ID \":=\" expresion ','",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : CTE_D",
"factor : '-' CTE_D",
"factor : CTE_USLINTEGER",
"factor : ID",
"condicion : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 7:
//#line 48 "gramaticaHH.Y"
{ }
break;
case 8:
//#line 49 "gramaticaHH.Y"
{}
break;
case 9:
//#line 52 "gramaticaHH.Y"
{}
break;
case 10:
//#line 54 "gramaticaHH.Y"
{}
break;
case 12:
//#line 60 "gramaticaHH.Y"
{}
break;
case 13:
//#line 61 "gramaticaHH.Y"
{}
break;
case 26:
//#line 86 "gramaticaHH.Y"
{}
break;
case 27:
//#line 89 "gramaticaHH.Y"
{}
break;
case 28:
//#line 90 "gramaticaHH.Y"
{}
break;
case 29:
//#line 91 "gramaticaHH.Y"
{}
break;
case 30:
//#line 95 "gramaticaHH.Y"
{}
break;
case 31:
//#line 96 "gramaticaHH.Y"
{}
break;
case 32:
//#line 97 "gramaticaHH.Y"
{}
break;
case 33:
//#line 101 "gramaticaHH.Y"
{}
break;
case 34:
//#line 102 "gramaticaHH.Y"
{}
break;
case 35:
//#line 103 "gramaticaHH.Y"
{}
break;
case 36:
//#line 104 "gramaticaHH.Y"
{}
break;
case 37:
//#line 107 "gramaticaHH.Y"
{}
break;
case 38:
//#line 109 "gramaticaHH.Y"
{}
break;
case 39:
//#line 110 "gramaticaHH.Y"
{}
break;
case 40:
//#line 111 "gramaticaHH.Y"
{}
break;
case 41:
//#line 112 "gramaticaHH.Y"
{}
break;
case 42:
//#line 113 "gramaticaHH.Y"
{}
break;
case 43:
//#line 114 "gramaticaHH.Y"
{}
break;
//#line 552 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################
/*-----------------ACA ARRANCAMOS NOSOTROS---------------------------------------------------   
        ------------------------------------------------------------
                    -------------------------*/
public Parser (ArchController lexico){
    this.lexico = lexico;
    
}

public int yylex(){
   Token token = lexico.getToken();
   int val = token.getTipo();
   yyval = new ParserVal(token);
   return val;
}

}
//################### END OF CLASS ##############################
